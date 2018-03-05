package ru.vassuv.processor.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import ru.vassuv.processor.annotation.Route
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class RouteProcessor : AbstractProcessor() {

    val FILE_NAME = "FrmFabric"
    val PACKAGE = "ru.vassuv.routing"

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Route::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }


    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val annotatedElements = roundEnv.getElementsAnnotatedWith(Route::class.java)

        val file = FileSpec.builder(PACKAGE, FILE_NAME)
                .addType(generateClass(annotatedElements))
                .build()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir, "$FILE_NAME.kt"))

        return true
    }

    private fun generateClass(annotatedElements: MutableSet<out Element>): TypeSpec {
        val frmFabricClass = TypeSpec.objectBuilder(FILE_NAME)

        annotatedElements
                .forEach {
                    val getterName = "get" + it.simpleName.toString()
                    frmFabricClass.addFunction(FunSpec.builder(getterName)
                            .addStatement("return \"World\"")
                            .build())
                }

        val getterName = "getInstance"
        frmFabricClass.addFunction(FunSpec.builder(getterName)
                .addStatement("return \"World\"")
                .build())
        return frmFabricClass.build()
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}