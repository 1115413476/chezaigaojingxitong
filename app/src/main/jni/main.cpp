#include <string.h>
#include <jni.h>
#include "main.h"
#include "geometry.h"

JNIEXPORT jint JNICALL Java_com_tusi_qdcloudcontrol_jni_getroadinfo_java2c( JNIEnv* env, jobject thiz ) {
    jint t1 = test();
    return t1;
}

