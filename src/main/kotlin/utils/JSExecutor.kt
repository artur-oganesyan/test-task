package utils

import com.codeborne.selenide.Selenide

class JSExecutor {

    fun insertText(text: String) {
        Selenide.webdriver().driver().executeJavaScript<String>(
            "window.editor.getDoc().setValue(\"$text\");"
        )
    }

}