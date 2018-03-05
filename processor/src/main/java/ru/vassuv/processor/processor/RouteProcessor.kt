package ru.vassuv.processor.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import ru.vassuv.processor.annotation.Route
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

@AutoService(Processor::class)
class RouteProcessor : AbstractProcessor() {

    val FILE_NAME = "FrmFabric"
    val PACKAGE = "ru.vassuv.processor"

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return linkedSetOf(Route::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    private var typeUtils: Types? = null
    private var elementUtils: Elements? = null

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)

        processingEnvironment ?: return

        typeUtils = processingEnvironment.typeUtils
        elementUtils = processingEnvironment.elementUtils
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {

        val annotatedElements = roundEnv.getElementsAnnotatedWith(annotations.find { it.simpleName.toString() == "Route" } ?: return false) ?: return false

        val file = FileSpec.builder(PACKAGE, FILE_NAME)
                .addType(generateClass(annotatedElements))
                .build()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir, "$FILE_NAME.kt"))

        return true
    }

    private fun generateClass(annotatedElements: MutableSet<out Element>): TypeSpec {
        val frmFabricClass = TypeSpec.objectBuilder(FILE_NAME)

        val bundleTypeName = elementUtils?.getTypeElement("android.os.Bundle")?.asType()?.asTypeName()?.asNullable()

        var fragmentTypeName: TypeName

        if (bundleTypeName != null)

        annotatedElements
                .forEach {
                    val getterName = "create" + it.simpleName.toString()

                    fragmentTypeName = it.asType().asTypeName()

                    frmFabricClass.addFunction(FunSpec.builder(getterName)
                            .addAnnotation(JvmStatic::class)
                            .addParameter("args", bundleTypeName)
                            .returns(fragmentTypeName)
                            .addStatement("val fragment = ${it.simpleName}()")
                            .addStatement("fragment.args = args")
                            .addStatement("return fragment")
                            .build())
                }

        return frmFabricClass.build()
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}