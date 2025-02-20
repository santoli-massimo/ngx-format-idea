package com.msan.ngxformatidea.injection

import com.intellij.lang.Language
import com.intellij.lang.css.CSSLanguage
import com.intellij.lang.html.HTMLLanguage
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.lang.javascript.JavaScriptSupportLoader.TYPESCRIPT
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.msan.ngxformatidea.language.NgxLanguage
import com.msan.ngxformatidea.psi.NgxFileType
import com.msan.ngxformatidea.utils.Logger
import com.intellij.lang.injection.InjectedLanguageManager;


class NgxMultiHostInjector : MultiHostInjector {
    override fun elementsToInjectIn(): List<Class<out PsiElement>> {
        return listOf(PsiLanguageInjectionHost::class.java)
//        return listOf(PsiElement::class.java)
    }

    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        val hostText = context.containingFile
        val host = context as PsiLanguageInjectionHost
        val virtualFile = context.containingFile?.virtualFile ?: return

//        val allLanguages = Language.getRegisteredLanguages()
//        Logger.warn("Linguaggi registrati: ${allLanguages.map { it.id }}")
//        Logger.warn("------------- ${virtualFile.fileType}")

        if(virtualFile.fileType !is NgxFileType) return

//        Logger.warn("############## INJECTION ################### ${host.text}")

//        injectLanguage("[template]", "[/template]", NgxLanguage.Angular2Html, registrar, host)
        injectLanguage("[template]", "[/template]", HTMLLanguage.INSTANCE, registrar, host)
        injectLanguage("[style]", "[/style]", CSSLanguage.INSTANCE, registrar, host)
        injectLanguage("[component]", "[/component]", TYPESCRIPT, registrar, host)

    }

    private fun injectLanguage(
        startMarker: String,
        endMarker: String,
        language: Language,
        registrar: MultiHostRegistrar,
        host: PsiLanguageInjectionHost,
    ){
        val ranges = getRangeFor(host.text, startMarker, endMarker) ?: return

        registrar.startInjecting(language)
            .addPlace(null, null, host, ranges)
            .doneInjecting()
    }

    fun getRangeFor(text: String, startTag: String, endTag: String): TextRange? {
        val startIndex = text.indexOf(startTag)
        val endIndex = text.indexOf(endTag)

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) { return null }

        val contentStart = startIndex + startTag.length
        val contentEnd = endIndex

        return TextRange(contentStart, contentEnd)
    }
}

//[TOML, ThymeleafExpressions, JPAQL, VueTS, InjectedFreeMarker, Angular181Html, DB2_IS, ECMAScript 6, BigQuery, ThymeleafTemplatesExpressions, Cockroach, ThymeleafIterateExpressions, JShellLanguage, prototext, JSRegexp, XPath, , SQLite, Flow JS, EJBQL, ThymeleafTemplatesFragmentExpressions, CSS, HQL, Angular181Svg, MySQL based, SQL92, H2, UastContextLanguage, EQL, HSQLDB, Oracle, Properties, TEXT, Angular2Svg, MongoDB-JSON, ClickHouse, Markdown, MongoJSExt, Vue, AZURE, SASS, Vertica, FTL], JVM, MongoJS, IgnoreLang, JSUnicodeRegexp, HiveQL, XsdRegExp, XML, MicronautDataQL, DB2_ZOS, yaml, OracleSqlPlus, Greenplum, Angular2Html, Exasol, JSP, ThymeleafUrlExpressions, UAST, DTD, FTL>, JQL, Snowflake, Manifest, SVG, Angular17Svg, SQLDateTime, HtmlCompatible, Lombok.Config, GitExclude, PostCSS, Redis, TSQL, JSPX, CouchbaseQuery, SPI, JSON, EditorConfig, HTTP Request, JavaScript, SparkSQL, Qute, Metadata JSON, HttpClientHandlerJavaScriptDialect, VueJS, XHTML, SCSS, HTML, kotlin, Declarative, Groovy, protobuf, JAVA, FTL, textmate, VueExpr, Shell Script, Dockerfile, GenericSQL, TypeScript, Redshift, MariaDB, GithubExpressionLanguage, Derby, LESS, JSON Lines, SpEL, Ngx, AOPTarget, SpringDataQL, DockerIgnore, EL, SQL, MongoDB, ThymeleafSpringSecurityExtras, Spring-MongoDB-JSON, JSONPath, JakartaDataQL, XPath2, HgIgnore, Cookie, RELAX-NG, protobase, DB2, PointcutExpression, YouTrack, Angular17Html, Dynamo, GitIgnore, VTL, Micronaut-MongoDB-JSON, RegExp, Angular2, CassandraQL, JQuery-CSS, MicronautEL, TypeScript JSX, CronExp, NetSuite, MySQL, ECMA Script Level 4, Sybase, JSON5, PostgreSQL, KND]