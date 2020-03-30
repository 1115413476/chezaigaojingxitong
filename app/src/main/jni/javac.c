#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_tusi_qdcloudcontrol_jni_getroadinfo_java2c( JNIEnv* env, jobject thiz ) {
    return (*env)->NewStringUTF(env, "Hello from JNI !");
}
