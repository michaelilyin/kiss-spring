package net.kiss.starter.service.utils

import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.util.ClassUtils
import java.lang.reflect.Method

fun <A : Annotation> findRepeatableAnnotation(
  method: Method,
  targetClass: Class<*>,
  annotationClass: Class<A>
): Set<A> {
  val specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass)
  val annotations = AnnotatedElementUtils.findMergedRepeatableAnnotations(specificMethod, annotationClass)

  if (annotations.isNotEmpty()) {
    return annotations
  }

  return findRepeatableAnnotationOnClass(specificMethod, method, annotationClass)
}

fun <A : Annotation> findAnnotation(
  method: Method,
  targetClass: Class<*>,
  annotationClass: Class<A>
): A? {
  val specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass)
  val annotation = AnnotationUtils.findAnnotation(specificMethod, annotationClass)

  if (annotation != null) {
    return annotation
  }

  return findAnnotationOnClass(specificMethod, method, annotationClass)
}

private fun <A : Annotation> findAnnotationOnClass(
  specificMethod: Method,
  method: Method,
  annotationClass: Class<A>
): A? {
  if (specificMethod !== method) {
    val annotation = AnnotationUtils.findAnnotation(method, annotationClass)

    if (annotation != null) {
      return annotation
    }
  }

  return AnnotationUtils.findAnnotation(specificMethod.declaringClass, annotationClass)
}

private fun <A : Annotation> findRepeatableAnnotationOnClass(
  specificMethod: Method,
  method: Method,
  annotationClass: Class<A>
): Set<A> {
  if (specificMethod !== method) {
    val annotations = AnnotatedElementUtils.findMergedRepeatableAnnotations(method, annotationClass)

    if (annotations.isNotEmpty()) {
      return annotations
    }
  }

  return AnnotatedElementUtils.findMergedRepeatableAnnotations(specificMethod.declaringClass, annotationClass)
}
