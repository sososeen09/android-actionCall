package com.sososeen09

import javassist.*
import org.gradle.api.Project

class CheckConditionInject {
    private static String injectStr = "System.out.println(\"Hello,Javassist\" ); ";

    static void injectDir(String path, String packageName, Project project) {
        def log = project.logger
        //project.android.bootClasspath 加入android.jar，否则找不到android相关的所有类
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->

                String filePath = file.absolutePath
                //确保当前文件是class文件，并且不是系统自动生成的class文件
                if (filePath.endsWith(".class")
                        && !filePath.contains('R$')
                        && !filePath.contains('R.class')
                        && !filePath.contains("BuildConfig.class")) {
                    // 判断当前目录是否是在我们的应用包里面
                    int index = filePath.indexOf(packageName);
                    boolean isMyPackage = index != -1;
                    if (isMyPackage) {
                        int end = filePath.length() - 6 // .class = 6
                        String className = filePath.substring(index, end)
                                .replace('\\', '.').replace('/', '.')

                        //开始修改class文件
                    }
                }
            }
        }
    }

    static def Pre_Finish = "  \n" +
            "    public void finish() {\n" +
            "           closeKeyboard();\n" +
            "        super.finish();\n" +
            "}"

    static def closeKeyboard_strinbg = '''
        private void closeKeyboard() {
        try {
            android.view.View view = getWindow().peekDecorView();
            if (view != null) {
                android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            //ignore
        }
       }
    '''
}
