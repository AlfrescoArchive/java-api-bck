/*
 * Copyright (C) 2005-2010 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.alfresco.repo.dictionary.DictionaryBootstrap;
import org.alfresco.repo.importer.ImporterBootstrap;
import org.alfresco.repo.management.subsystems.ChildApplicationContextFactory;
import org.alfresco.repo.management.subsystems.PropertyBackedBeanRegistry;
import org.alfresco.repo.policy.PolicyComponentImpl;
import org.alfresco.repo.security.permissions.impl.model.PermissionModelBootstrap;
import org.alfresco.repo.workflow.WorkflowDeployer;
import org.alfresco.util.ApplicationContextHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.util.PropertiesPersister;

/**
 * Test to verify the spring beans required for extension points are backwards compatible.
 *
 * @author Gavin Cornwell
 */
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
            assertNotNull("Expected to find setModels method on DictionaryBootstrap", setModelsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setModels method on DictionaryBootstrap");
        }
        
        try
        {
            Method setLabelsMethod = dictionaryBootstrapClass.getDeclaredMethod("setLabels", new Class[]{List.class});
            assertNotNull("Expected to find setLabels method on DictionaryBootstrap", setLabelsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setLabels method on DictionaryBootstrap");
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
            assertNotNull("Expected to find setModel method on PermissionModelBootstrap", setModelMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setModel method on PermissionModelBootstrap");
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
        
        // ensure the required setters are still present
        Class<ChildApplicationContextFactory> childAppContextFactoryClass = ChildApplicationContextFactory.class;
        
        try
        {
            Method setRegistryMethod = childAppContextFactoryClass.getMethod("setRegistry", 
                        new Class[]{PropertyBackedBeanRegistry.class});
            assertNotNull("Expected to find setRegistry method on ChildApplicationContextFactory", setRegistryMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setRegistry method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setAutoStartMethod = childAppContextFactoryClass.getMethod("setAutoStart", new Class[]{boolean.class});
            assertNotNull("Expected to find setAutoStart method on ChildApplicationContextFactory", setAutoStartMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setAutoStart method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setBeanNameMethod = childAppContextFactoryClass.getMethod("setBeanName", new Class[]{String.class});
            assertNotNull("Expected to find setBeanName method on ChildApplicationContextFactory", setBeanNameMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setBeanName method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setCategoryMethod = childAppContextFactoryClass.getMethod("setCategory", new Class[]{String.class});
            assertNotNull("Expected to find setCategory method on ChildApplicationContextFactory", setCategoryMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setCategory method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setInstancePathMethod = childAppContextFactoryClass.getMethod("setInstancePath", new Class[]{List.class});
            assertNotNull("Expected to find setInstancePath method on ChildApplicationContextFactory", setInstancePathMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setInstancePath method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setPropertyDefaultsMethod = childAppContextFactoryClass.getMethod("setPropertyDefaults", 
                        new Class[]{Properties.class});
            assertNotNull("Expected to find setPropertyDefaults method on ChildApplicationContextFactory", 
                        setPropertyDefaultsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setPropertyDefaults method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setEncryptedPropertyDefaultsMethod = childAppContextFactoryClass.getMethod("setEncryptedPropertyDefaults", 
                        new Class[]{Properties.class});
            assertNotNull("Expected to find setEncryptedPropertyDefaults method on ChildApplicationContextFactory", 
                        setEncryptedPropertyDefaultsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setEncryptedPropertyDefaults method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setSaveSetPropertyMethod = childAppContextFactoryClass.getMethod("setSaveSetProperty", 
                        new Class[]{boolean.class});
            assertNotNull("Expected to find setSaveSetProperty method on ChildApplicationContextFactory", setSaveSetPropertyMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setSaveSetProperty method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setCompositePropertyTypesMethod = childAppContextFactoryClass.getDeclaredMethod("setCompositePropertyTypes", 
                        new Class[]{Map.class});
            assertNotNull("Expected to find setCompositePropertyTypes method on ChildApplicationContextFactory", 
                        setCompositePropertyTypesMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setCompositePropertyTypes method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setPersisterMethod = childAppContextFactoryClass.getDeclaredMethod("setPersister", 
                        new Class[]{PropertiesPersister.class});
            assertNotNull("Expected to find setPersister method on ChildApplicationContextFactory", setPersisterMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setPersister method on ChildApplicationContextFactory");
        }
        
        try
        {
            Method setTypeNameMethod = childAppContextFactoryClass.getDeclaredMethod("setTypeName", new Class[]{String.class});
            assertNotNull("Expected to find setTypeName method on ChildApplicationContextFactory", setTypeNameMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setTypeName method on ChildApplicationContextFactory");
        }
    }
    
    @Test
    public void testModules()
    {
        // ensure the "module.baseComponent" is present
        assertTrue(beanDefinitionNames.contains("module.baseComponent"));
    }

    @Test
    public void testImporterBeans()
    {
        // ensure the required setters are still present
        Class<ImporterBootstrap> importerBootstrapClass = ImporterBootstrap.class;
        
        try
        {
            Method setBootstrapViewsMethod = importerBootstrapClass.getDeclaredMethod("setBootstrapViews", 
                        new Class[]{List.class});
            assertNotNull("Expected to find setBootstrapViews method on ImporterBootstrap", setBootstrapViewsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setBootstrapViews method on ImporterBootstrap");
        }
        
        // ensure the spacesStoreImporter abstract bean is still present
        assertTrue(beanDefinitionNames.contains("spacesStoreImporter"));
    }
    
    @Test
    public void testWorkflowBeans()
    { 
        // ensure the required setters are still present
        Class<WorkflowDeployer> workflowDeployerClass = WorkflowDeployer.class;
        
        try
        {
            Method setWorkflowDefinitionsMethod = workflowDeployerClass.getDeclaredMethod("setWorkflowDefinitions", 
                        new Class[]{List.class});
            assertNotNull("Expected to find setWorkflowDefinitions method on WorkflowDeployer", setWorkflowDefinitionsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setWorkflowDefinitions method on WorkflowDeployer");
        }
        
        try
        {
            Method setModelsMethod = workflowDeployerClass.getDeclaredMethod("setModels", new Class[]{List.class});
            assertNotNull("Expected to find setModels method on WorkflowDeployer", setModelsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setModels method on WorkflowDeployer");
        }
        
        try
        {
            Method setLabelsMethod = workflowDeployerClass.getDeclaredMethod("setLabels", new Class[]{List.class});
            assertNotNull("Expected to find setLabels method on WorkflowDeployer", setLabelsMethod);
        }
        catch (NoSuchMethodException error)
        {
            fail("Expected to find setLabels method on WorkflowDeployer");
        }
        
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
