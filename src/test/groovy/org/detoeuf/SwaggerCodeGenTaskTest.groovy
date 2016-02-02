package org.detoeuf

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertTrue

class SwaggerCodeGenTaskTest {
    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        def task = project.task('swagger', type: SwaggerCodeGenTask)
        assertTrue(task instanceof SwaggerCodeGenTask)
    }

   // @Ignore("Wait until swagger-codegen detects file type correctly.  Version 2.1.5 (?) cannot get yaml file from classpath which is why this test is ignored")
    @Test
    public void basicConfiguration() {

        URL url = Thread.currentThread().getContextClassLoader().getResource("petstore.yaml");
        File file = new File(url.getPath());

        Project project = ProjectBuilder.builder().build()
        project.set('swaggerInputSpec', file.toString())
        project.set('swaggerApiPackage', 'com.orange.testApi')
        project.set('swaggerInvokerPackage', 'com.orange.testPackage')
        project.set('swaggerModelPackage', 'com.orange.testModel')
        project.set('swaggerLanguage', 'java')
        project.set('swaggerOutput', 'target/generated-sources/swagger')
        project.set('swaggerSrc', 'src/swagger')
        project.set('swaggerLibrary', 'okhttp-gson')
        def task = project.task('swagger', type: SwaggerCodeGenTask, dependsOn: 'processTestResources')
        assertTrue(task instanceof SwaggerCodeGenTask)
        task.execute()
    }

}
