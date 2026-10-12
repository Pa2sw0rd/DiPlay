#include "app_security.h"
#include <jni.h>
#include <stdint.h>
#include <stddef.h>
#include <string.h>
#include <stdio.h>
#include <android/log.h>

#define LOG_TAG "DiPlaySecurity"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

static volatile int g_security_verified = 0;

int app_security_is_verified(void) {
    return g_security_verified == 1;
}

void app_security_mark_verified(void) {
    g_security_verified = 1;
}

// SHA-256 implementation
typedef struct {
    uint32_t state[8];
    uint64_t count;
    uint8_t buffer[64];
} SHA256_CTX;

static const uint32_t K[64] = {
    0x428a2f98,0x71374491,0xb5c0fbcf,0xe9b5dba5,0x3956c25b,0x59f111f1,0x923f82a4,0xab1c5ed5,
    0xd807aa98,0x12835b01,0x243185be,0x550c7dc3,0x72be5d74,0x80deb1fe,0x9bdc06a7,0xc19bf174,
    0xe49b69c1,0xefbe4786,0x0fc19dc6,0x240ca1cc,0x2de92c6f,0x4a7484aa,0x5cb0a9dc,0x76f988da,
    0x983e5152,0xa831c66d,0xb00327c8,0xbf597fc7,0xc6e00bf3,0xd5a79147,0x06ca6351,0x14292967,
    0x27b70a85,0x2e1b2138,0x4d2c6dfc,0x53380d13,0x650a7354,0x766a0abb,0x81c2c92e,0x92722c85,
    0xa2bfe8a1,0xa81a664b,0xc24b8b70,0xc76c51a3,0xd192e819,0xd6990624,0xf40e3585,0x106aa070,
    0x19a4c116,0x1e376c08,0x2748774c,0x34b0bcb5,0x391c0cb3,0x4ed8aa4a,0x5b9cca4f,0x682e6ff3,
    0x748f82ee,0x78a5636f,0x84c87814,0x8cc70208,0x90befffa,0xa4506ceb,0xbef9a3f7,0xc67178f2
};

#define ROR(x, n) (((x) >> (n)) | ((x) << (32 - (n))))
#define CH(x, y, z) (((x) & (y)) ^ (~(x) & (z)))
#define MAJ(x, y, z) (((x) & (y)) ^ ((x) & (z)) ^ ((y) & (z)))
#define EP0(x) (ROR(x, 2) ^ ROR(x, 13) ^ ROR(x, 22))
#define EP1(x) (ROR(x, 6) ^ ROR(x, 11) ^ ROR(x, 25))
#define SIG0(x) (ROR(x, 7) ^ ROR(x, 18) ^ ((x) >> 3))
#define SIG1(x) (ROR(x, 17) ^ ROR(x, 19) ^ ((x) >> 10))

static void sha256_transform(SHA256_CTX *ctx, const uint8_t data[64]) {
    uint32_t a, b, c, d, e, f, g, h, t1, t2, m[64];
    for (int i = 0, j = 0; i < 16; ++i, j += 4)
        m[i] = ((uint32_t)data[j] << 24) | ((uint32_t)data[j + 1] << 16) | ((uint32_t)data[j + 2] << 8) | ((uint32_t)data[j + 3]);
    for (int i = 16; i < 64; ++i)
        m[i] = SIG1(m[i - 2]) + m[i - 7] + SIG0(m[i - 15]) + m[i - 16];

    a = ctx->state[0]; b = ctx->state[1]; c = ctx->state[2]; d = ctx->state[3];
    e = ctx->state[4]; f = ctx->state[5]; g = ctx->state[6]; h = ctx->state[7];

    for (int i = 0; i < 64; ++i) {
        t1 = h + EP1(e) + CH(e, f, g) + K[i] + m[i];
        t2 = EP0(a) + MAJ(a, b, c);
        h = g; g = f; f = e; e = d + t1;
        d = c; c = b; b = a; a = t1 + t2;
    }

    ctx->state[0] += a; ctx->state[1] += b; ctx->state[2] += c; ctx->state[3] += d;
    ctx->state[4] += e; ctx->state[5] += f; ctx->state[6] += g; ctx->state[7] += h;
}

static void sha256_init(SHA256_CTX *ctx) {
    ctx->count = 0;
    ctx->state[0] = 0x6a09e667; ctx->state[1] = 0xbb67ae85;
    ctx->state[2] = 0x3c6ef372; ctx->state[3] = 0xa54ff53a;
    ctx->state[4] = 0x510e527f; ctx->state[5] = 0x9b05688c;
    ctx->state[6] = 0x1f83d9ab; ctx->state[7] = 0x5be0cd19;
}

static void sha256_update(SHA256_CTX *ctx, const uint8_t *data, size_t len) {
    size_t i = 0;
    size_t index = (size_t)((ctx->count >> 3) & 63);
    ctx->count += (uint64_t)len << 3;
    size_t partLen = 64 - index;

    if (len >= partLen) {
        memcpy(&ctx->buffer[index], data, partLen);
        sha256_transform(ctx, ctx->buffer);
        for (i = partLen; i + 63 < len; i += 64)
            sha256_transform(ctx, &data[i]);
        index = 0;
    }
    memcpy(&ctx->buffer[index], &data[i], len - i);
}

static void sha256_final(SHA256_CTX *ctx, uint8_t hash[32]) {
    uint8_t bits[8];
    for (int i = 0; i < 8; ++i)
        bits[i] = (uint8_t)((ctx->count >> ((7 - i) * 8)) & 0xff);

    size_t index = (size_t)((ctx->count >> 3) & 63);
    size_t padLen = (index < 56) ? (56 - index) : (120 - index);
    static const uint8_t PADDING[64] = { 0x80 };
    sha256_update(ctx, PADDING, padLen);
    sha256_update(ctx, bits, 8);

    for (int i = 0; i < 8; ++i) {
        hash[i * 4]     = (uint8_t)((ctx->state[i] >> 24) & 0xff);
        hash[i * 4 + 1] = (uint8_t)((ctx->state[i] >> 16) & 0xff);
        hash[i * 4 + 2] = (uint8_t)((ctx->state[i] >> 8) & 0xff);
        hash[i * 4 + 3] = (uint8_t)(ctx->state[i] & 0xff);
    }
}

static void calc_sha256(const uint8_t *data, size_t len, uint8_t hash[32]) {
    SHA256_CTX ctx;
    sha256_init(&ctx);
    sha256_update(&ctx, data, len);
    sha256_final(&ctx, hash);
}

// Obfuscated secrets
#define PKG_LEN 17
#define PKG_KEY 0x3C
static const uint8_t ENC_PKG[PKG_LEN] = {
    0x5F, 0x53, 0x51, 0x12, 0x4F, 0x54, 0x55, 0x54, 0x5D, 0x5E, 0x12, 0x58, 0x55, 0x4C, 0x50, 0x5D, 0x45
};

#define SIG_KEY 0x5A
static const uint8_t ENC_SIG_HASH[32] = {
    0x7E, 0x6C, 0xC9, 0x31, 0xAC, 0xE3, 0x31, 0x3E,
    0x0E, 0x29, 0x37, 0x52, 0xFA, 0xF1, 0xD7, 0x36,
    0xCF, 0x9D, 0x40, 0xF1, 0xF5, 0x8B, 0x42, 0x4A,
    0x7B, 0x3F, 0x2D, 0x91, 0xB8, 0x41, 0x76, 0xA6
};

static int verify_package_name(const char *pkg) {
    if (!pkg) return 0;
    size_t len = strlen(pkg);
    if (len != PKG_LEN) return 0;
    for (size_t i = 0; i < PKG_LEN; i++) {
        if ((uint8_t)pkg[i] != (uint8_t)(ENC_PKG[i] ^ PKG_KEY)) {
            return 0;
        }
    }
    return 1;
}

static int check_process_cmdline(void) {
    FILE *fp = fopen("/proc/self/cmdline", "r");
    if (!fp) return 1; // Not readable on some SELinux policies; defer to package manager check
    char cmdline[64];
    memset(cmdline, 0, sizeof(cmdline));
    size_t n = fread(cmdline, 1, sizeof(cmdline) - 1, fp);
    fclose(fp);
    if (n == 0) return 1;

    for (size_t i = 0; i < PKG_LEN && i < n; i++) {
        if ((uint8_t)cmdline[i] != (uint8_t)(ENC_PKG[i] ^ PKG_KEY)) {
            return 0; // cmdline tampered
        }
    }
    return 1;
}

static int verify_signature_hash(const uint8_t *hash) {
    for (int i = 0; i < 32; i++) {
        if (hash[i] != (uint8_t)(ENC_SIG_HASH[i] ^ SIG_KEY)) {
            return 0;
        }
    }
    return 1;
}

JNIEXPORT jint JNICALL Java_com_shilapi_xcertplay_security_NativeSecurity_verify(JNIEnv *env, jclass clazz, jobject context) {
    (void) clazz;
    if (context == NULL) return 101;

    if (!check_process_cmdline()) {
        LOGE("Process cmdline verification failed");
        return 102;
    }

    jclass context_cls = (*env)->GetObjectClass(env, context);
    if (!context_cls) return 103;

    jmethodID mid_getPkg = (*env)->GetMethodID(env, context_cls, "getPackageName", "()Ljava/lang/String;");
    if (!mid_getPkg) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 104;
    }

    jstring jpkg = (jstring)(*env)->CallObjectMethod(env, context, mid_getPkg);
    if (!jpkg) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 105;
    }

    const char *pkg_chars = (*env)->GetStringUTFChars(env, jpkg, NULL);
    int pkg_ok = verify_package_name(pkg_chars);
    (*env)->ReleaseStringUTFChars(env, jpkg, pkg_chars);
    if (!pkg_ok) {
        LOGE("Package name verification failed");
        return 106;
    }

    jmethodID mid_getPm = (*env)->GetMethodID(env, context_cls, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    if (!mid_getPm) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 107;
    }

    jobject pm = (*env)->CallObjectMethod(env, context, mid_getPm);
    if (!pm) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 108;
    }

    jclass pm_cls = (*env)->GetObjectClass(env, pm);
    jmethodID mid_getPi = (*env)->GetMethodID(env, pm_cls, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    if (!mid_getPi) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 109;
    }

    jobject pi = (*env)->CallObjectMethod(env, pm, mid_getPi, jpkg, (jint)64); // 64 = GET_SIGNATURES
    if (!pi || (*env)->ExceptionCheck(env)) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 110;
    }

    jclass pi_cls = (*env)->GetObjectClass(env, pi);
    jfieldID fid_signatures = (*env)->GetFieldID(env, pi_cls, "signatures", "[Landroid/content/pm/Signature;");
    if (!fid_signatures) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 111;
    }

    jobjectArray sig_arr = (jobjectArray)(*env)->GetObjectField(env, pi, fid_signatures);
    if (!sig_arr || (*env)->GetArrayLength(env, sig_arr) == 0) {
        return 112;
    }

    jobject sig_obj = (*env)->GetObjectArrayElement(env, sig_arr, 0);
    if (!sig_obj) return 113;

    jclass sig_cls = (*env)->GetObjectClass(env, sig_obj);
    jmethodID mid_toByte = (*env)->GetMethodID(env, sig_cls, "toByteArray", "()[B");
    if (!mid_toByte) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 114;
    }

    jbyteArray der_bytes = (jbyteArray)(*env)->CallObjectMethod(env, sig_obj, mid_toByte);
    if (!der_bytes) {
        if ((*env)->ExceptionCheck(env)) (*env)->ExceptionClear(env);
        return 115;
    }

    jsize der_len = (*env)->GetArrayLength(env, der_bytes);
    if (der_len <= 0) return 116;

    jbyte *der_ptr = (*env)->GetByteArrayElements(env, der_bytes, NULL);
    if (!der_ptr) return 117;

    uint8_t hash[32];
    calc_sha256((const uint8_t*)der_ptr, (size_t)der_len, hash);
    (*env)->ReleaseByteArrayElements(env, der_bytes, der_ptr, JNI_ABORT);

    if (!verify_signature_hash(hash)) {
        LOGE("Signature SHA-256 mismatch");
        return 118;
    }

    g_security_verified = 1;
    return 0;
}
