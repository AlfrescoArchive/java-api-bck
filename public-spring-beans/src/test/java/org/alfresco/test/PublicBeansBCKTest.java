package org.alfresco.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.alfresco.repo.dictionary.DictionaryBootstrap;
import org.alfresco.repo.policy.PolicyComponentImpl;
import org.alfresco.repo.security.permissions.impl.model.PermissionModelBootstrap;
import org.alfresco.util.ApplicationContextHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class PublicBeansBCKTest
{
    private static ApplicationContext ctx;
    private static List<String> beanDefinitionNames; 
    
    @BeforeClass
    public static void setupBeanDefinitionNames()
    {
        // setup default spring context
        ctx = ApplicationContextHelper.getApplicationContext();
        
        // get bean definition names
        String definitions[] = ctx.getBeanDefinitionNames();
        beanDefinitionNames = Arrays.asList(definitions);
    }
    
    @Test
    public void testActionBeans()
    {
        // ensure the action-executer abstract bean is still present
        assertTrue(beanDefinitionNames.contains("action-executer"));
    }
    
    @Test 
    public void testDictionaryBeans()
    {
        // ensure the dictionaryModelBootstrap abstract bean is still present
        assertTrue(beanDefinitionNames.contains("dictionaryModelBootstrap"));
        
        // ensure the dictionaryBootstrap bean is still present
        Object dictionaryBootstrapBean = ctx.getBean("dictionaryBootstrap");
        assertNotNull("Expected to find the 'dictionaryBootstrap' bean", dictionaryBootstrapBean);
        assertTrue("Expected 'dictionaryBootstrap' to be an instance of DictionaryBootstrap",
                    (dictionaryBootstrapBean instanceof DictionaryBootstrap));
        
        // ensure the required setters are still present
        Class<DictionaryBootstrap> dictionaryBootstrapClass = DictionaryBootstrap.class;
        
        try
        {
            Method setModelsMethod = dictionaryBootstrapClass.getDeclaredMethod("setModels", new Class[]{List.class});
            assertNotNull("Expected to find setModels methods on DictionaryBootstrap", setModelsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setModels methods on DictionaryBootstrap");
        }
        
        try
        {
            Method setLabelsMethod = dictionaryBootstrapClass.getDeclaredMethod("setLabels", new Class[]{List.class});
            assertNotNull("Expected to find setLabels methods on DictionaryBootstrap", setLabelsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setLabels methods on DictionaryBootstrap");
        }
    }
    
    @Test
    public void testPermissionBeans()
    {
        // ensure the permissionModelBootstrap abstract bean is still present
        assertTrue(beanDefinitionNames.contains("permissionModelBootstrap"));
        
        // ensure the required setters are still present
        Class<PermissionModelBootstrap> permissionModelClass = PermissionModelBootstrap.class;
        
        try
        {
            Method setModelMethod = permissionModelClass.getDeclaredMethod("setModel", new Class[]{String.class});
            assertNotNull("Expected to find setModel methods on PermissionModelBootstrap", setModelMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setModel methods on PermissionModelBootstrap");
        }
    }
    
    @Test
    public void testJobBeans()
    {
        // ensure the schedulerFactory bean is still present
        Object schedulerFactoryBean = ctx.getBean("schedulerFactory");
        assertNotNull("Expected to find the 'schedulerFactory' bean", schedulerFactoryBean);
    }
    
    @Test
    public void testPolicyBeans()
    {
        // ensure the policyComponent bean is still present
        Object policyComponentBean = ctx.getBean("policyComponent");
        assertNotNull("Expected to find the 'policyComponent' bean", policyComponentBean);
        assertTrue("Expected 'policyComponent' to be an instance of PolicyComponentImpl",
                    (policyComponentBean instanceof PolicyComponentImpl));
        
        // ensure the policyRegistration abstract bean is still present
        assertTrue(beanDefinitionNames.contains("policyRegistration"));
    }
    
    @Test
    public void testSubsystemsBeans()
    {
        // ensure the abstractPropertyBackedBean abstract bean is still present
        assertTrue(beanDefinitionNames.contains("abstractPropertyBackedBean"));
        
        // setter methods of ChildApplicationContextFactory
    }
    
    @Test
    public void testImporterBeans()
    {
        // setter methods of ImporterBootstrap
        
        // ensure the spacesStoreImporter abstract bean is still present
        assertTrue(beanDefinitionNames.contains("spacesStoreImporter"));
    }
    
    @Test
    public void testWorkflowBeans()
    {
        // setter methods of WorkflowDeployer 
        
        // ensure the workflowDeployer abstract bean is still present
        assertTrue(beanDefinitionNames.contains("workflowDeployer"));
    }
    
    @Test
    public void testRatingsBeans()
    {
        // ensure the baseRatingScheme abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseRatingScheme"));
        
        // ensure the baseRollupAlgorithm abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseRollupAlgorithm")); 
    }
    
    @Test
    public void testContentBeans()
    {
        // ensure the baseMetadataExtracter abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseMetadataExtracter"));
        
        // ensure the baseContentTransformer abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseContentTransformer"));
    }
    
    @Test
    public void testProcessorBeans()
    {
        // ensure the baseJavaScriptExtension abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseJavaScriptExtension"));
        
        // ensure the baseTemplateProcessor abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseTemplateProcessor"));
        
        // ensure the baseTemplateImplementation abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseTemplateImplementation"));
    }
    
    @Test
    public void testFormBeans()
    {
        // ensure the baseFormProcessor abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseFormProcessor"));
        
        // ensure the baseFormFilter abstract bean is still present
        assertTrue(beanDefinitionNames.contains("baseFormFilter"));
    }
}
