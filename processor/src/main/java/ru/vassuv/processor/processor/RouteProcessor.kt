package ru.vassuv.processor.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import ru.vassuv.processor.annotation.Route
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
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

        val annotatedElements = roundEnv.getElementsAnnotatedWith(annotations.find { it.simpleName.toString() == "Route" }
                ?: return false) ?: return false

        val file = FileSpec.builder(PACKAGE, FILE_NAME)
                .addComment("%S", getCommentFile())
                .addType(generateClass(annotatedElements))
                .build()

        val kaptKotlinGeneratedDir = "/home/developer/git/Start-App/app/src/main/java/ru/vassuv/generated"
//        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir, "$FILE_NAME.kt"))

        return true
    }

    private fun generateClass(annotatedElements: MutableSet<out Element>): TypeSpec {
        val frmFabricClass = TypeSpec.objectBuilder(FILE_NAME)

        val bundleTypeName = elementUtils?.getTypeElement("android.os.Bundle")?.asType()?.asTypeName()?.asNullable()
        val supportFragmentTypeName = elementUtils?.getTypeElement("android.support.v4.app.Fragment")?.asType()?.asTypeName()

        var fragmentTypeName: TypeName
        var className: String
        var getterName: String
        var annotatedName: String
        var valueName: String

        if (bundleTypeName != null && supportFragmentTypeName != null) {
            val bundleArgsParameter = ParameterSpec.builder("args", bundleTypeName).defaultValue("null").build()

            val createBaseFunction = FunSpec.builder("createFragment")
                    .addAnnotation(JvmStatic::class)
                    .addParameter("fragmentName", String::class)
                    .addParameter(bundleArgsParameter)
                    .returns(supportFragmentTypeName)
                    .addStatement("val fragment: Fragment = when(fragmentName) {")

            val createValueOfFunction = FunSpec.builder("valueOf")
                    .addAnnotation(JvmStatic::class)
                    .addParameter("fragment", supportFragmentTypeName)
                    .returns(String::class)
                    .addStatement("val name: String = when(fragment::class) {")

            annotatedElements.forEachIndexed { index, element ->
                className = element.simpleName.toString()
                getterName = "create" + className
                annotatedName = element.getAnnotation(Route::class.java).name
                valueName = if (annotatedName.isEmpty()) createPropName(className) else annotatedName

                fragmentTypeName = element.asType().asTypeName()
                frmFabricClass
                        .addFunction(createFunctionSpec(getterName, bundleArgsParameter, fragmentTypeName, element))
                        .addProperty(createValueSpec(valueName, className))

                createBaseFunction.addStatement("    $valueName -> ${element.simpleName}()")
                createValueOfFunction.addStatement("    $className::class -> $valueName")

            }

            createBaseFunction
                    .addStatement("    else -> throw Exception(\"Передан не аннатируемый фрагмент\")")
                    .addStatement("}")
                    .addStatement("fragment.arguments = args")
                    .addStatement("return fragment")

            createValueOfFunction
                    .addStatement("    else -> \"\"")
                    .addStatement("}")
                    .addStatement("return name")

            frmFabricClass.addFunction(createBaseFunction.build())

            frmFabricClass.addFunction(createValueOfFunction.build())
        }

        return frmFabricClass.build()
    }

    private fun createValueSpec(propName: String, className: String): PropertySpec {
        return PropertySpec
                .builder(
                        propName,
                        String::class.asTypeName(),
                        KModifier.PUBLIC)
                .initializer("\"$propName\" // $className")
                .build()
    }

    private fun createFunctionSpec(getterName: String, bundleArgsParametr: ParameterSpec, fragmentTypeName: TypeName, it: Element): FunSpec {
        return FunSpec.builder(getterName)
                .addAnnotation(JvmStatic::class)
                .addParameter(bundleArgsParametr)
                .returns(fragmentTypeName)
                .addStatement("val fragment = ${it.simpleName}()")
                .addStatement("fragment.arguments = args")
                .addStatement("return fragment")
                .addModifiers(KModifier.PRIVATE)
                .build()
    }

    private fun createPropName(classFragmentName: String): String {
        val changedName = classFragmentName
                .replace("Fragment$".toRegex(), "")
                .replace("([a-z]+)".toRegex(), { matchResult -> matchResult.groupValues[0].toUpperCase() + "_" })
                .trimEnd('_')
        return changedName
    }

    private fun getCommentFile(): String {
        return """\*
                        ********************************
                        *      Генерируемый файл       *
                        ********************************
                        *                              *
                        *                              *
                        *                              *
                        *                              *
                        *                              *
                        *                              *
                        *                              *
                        *                              *
                        *                              *
                        ********************************
                    *\"""
    }
}
