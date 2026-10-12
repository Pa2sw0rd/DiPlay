#ifndef APP_SECURITY_H
#define APP_SECURITY_H

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

int app_security_is_verified(void);
void app_security_mark_verified(void);

JNIEXPORT jint JNICALL Java_com_shilapi_xcertplay_security_NativeSecurity_verify(JNIEnv *env, jclass clazz, jobject context);

#ifdef __cplusplus
}
#endif

#endif // APP_SECURITY_H
