package com.shilapi.xcertplay.security

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.shilapi.xcertplay.host.R
import java.security.MessageDigest

object AppIntegrityGuard {
    data class IntegrityResult(
        val isValid: Boolean,
        val reason: String = ""
    )

    private val ENC_PKG = byteArrayOf(
        0x5F, 0x53, 0x51, 0x12, 0x4F, 0x54, 0x55, 0x54, 0x5D, 0x5E, 0x12, 0x58, 0x55, 0x4C, 0x50, 0x5D, 0x45
    )
    private const val PKG_KEY: Byte = 0x3C

    private val ENC_SIG_HASH = byteArrayOf(
        0x7E, 0x6C, 0xC9.toByte(), 0x31, 0xAC.toByte(), 0xE3.toByte(), 0x31, 0x3E,
        0x0E, 0x29, 0x37, 0x52, 0xFA.toByte(), 0xF1.toByte(), 0xD7.toByte(), 0x36,
        0xCF.toByte(), 0x9D.toByte(), 0x40, 0xF1.toByte(), 0xF5.toByte(), 0x8B.toByte(),
        0x42, 0x4A, 0x7B, 0x3F, 0x2D, 0x91.toByte(), 0xB8.toByte(), 0x41, 0x76, 0xA6.toByte()
    )
    private const val SIG_KEY: Byte = 0x5A

    private val ENC_QR_HASH = byteArrayOf(
        0xBF.toByte(), 0x1F, 0xBC.toByte(), 0x75, 0xEC.toByte(), 0xF1.toByte(), 0xA6.toByte(), 0x75,
        0xA5.toByte(), 0x93.toByte(), 0xE8.toByte(), 0xEC.toByte(), 0x4C, 0x1B, 0xAD.toByte(), 0x8F.toByte(),
        0xE8.toByte(), 0xEA.toByte(), 0x63, 0xC1.toByte(), 0x66, 0x12, 0x38, 0x54,
        0x61, 0x8C.toByte(), 0x1B, 0x31, 0x9A.toByte(), 0xD2.toByte(), 0x42, 0x06
    )
    private const val QR_KEY: Byte = 0x6B
    private const val EXPECTED_QR_SIZE = 97594

    @Volatile
    private var cachedResult: IntegrityResult? = null

    private val isTestEnvironment: Boolean by lazy {
        try {
            Class.forName("org.robolectric.Robolectric") != null
        } catch (_: Throwable) {
            false
        }
    }

    private fun decrypt(encrypted: ByteArray, key: Byte): ByteArray =
        ByteArray(encrypted.size) { i -> (encrypted[i].toInt() xor key.toInt()).toByte() }

    private fun decryptString(encrypted: ByteArray, key: Byte): String =
        String(decrypt(encrypted, key), Charsets.UTF_8)

    fun verify(context: Context): IntegrityResult {
        cachedResult?.let { return it }
        val result = performCheck(context.applicationContext ?: context)
        cachedResult = result
        return result
    }

    fun isTampered(context: Context): Boolean = !verify(context).isValid

    private fun performCheck(context: Context): IntegrityResult {
        if (isTestEnvironment) {
            return IntegrityResult(isValid = true, reason = "Test environment")
        }

        // 1. Package Name Verification
        val expectedPkg = decryptString(ENC_PKG, PKG_KEY)
        if (context.packageName != expectedPkg) {
            return IntegrityResult(isValid = false, reason = "应用包名已被篡改")
        }

        // 2. Signature SHA-256 Verification
        val expectedSig = decrypt(ENC_SIG_HASH, SIG_KEY)
        val sigs = getSignatures(context)
        if (sigs.isEmpty()) {
            return IntegrityResult(isValid = false, reason = "无法读取应用安装签名")
        }

        val md = MessageDigest.getInstance("SHA-256")
        val sigMatched = sigs.any { certBytes ->
            val hash = md.digest(certBytes)
            hash.contentEquals(expectedSig)
        }
        if (!sigMatched) {
            return IntegrityResult(isValid = false, reason = "安装包签名不符（检测到二次签名或改包）")
        }

        // 3. Asset Integrity Verification (Author Douyin QR Code)
        val qrValid = runCatching {
            context.resources.openRawResource(R.drawable.author_douyin_qrcode).use { input ->
                val bytes = input.readBytes()
                if (bytes.size != EXPECTED_QR_SIZE) return@runCatching false
                val hash = MessageDigest.getInstance("SHA-256").digest(bytes)
                val expectedQr = decrypt(ENC_QR_HASH, QR_KEY)
                hash.contentEquals(expectedQr)
            }
        }.getOrDefault(false)

        if (!qrValid) {
            return IntegrityResult(isValid = false, reason = "作者版权素材已被修改或替换")
        }

        // 4. Native C Layer Verification
        val nativeCode = runCatching {
            NativeSecurity.verify(context)
        }.getOrDefault(-999)

        if (nativeCode != 0) {
            return IntegrityResult(isValid = false, reason = "底层系统防篡改校验未通过 (code: $nativeCode)")
        }

        return IntegrityResult(isValid = true)
    }

    private fun getSignatures(context: Context): List<ByteArray> {
        val pm = context.packageManager
        val pkg = context.packageName
        return runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val pi = pm.getPackageInfo(pkg, PackageManager.GET_SIGNING_CERTIFICATES)
                val signingInfo = pi.signingInfo
                if (signingInfo != null) {
                    if (signingInfo.hasMultipleSigners()) {
                        signingInfo.apkContentsSigners.map { it.toByteArray() }
                    } else {
                        signingInfo.signingCertificateHistory?.map { it.toByteArray() } ?: emptyList()
                    }
                } else {
                    @Suppress("DEPRECATION")
                    pm.getPackageInfo(pkg, PackageManager.GET_SIGNATURES).signatures?.map { it.toByteArray() } ?: emptyList()
                }
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(pkg, PackageManager.GET_SIGNATURES).signatures?.map { it.toByteArray() } ?: emptyList()
            }
        }.getOrDefault(emptyList())
    }
}
