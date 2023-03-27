package com.group.KGMS.utils;

import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFWriter;
import org.springframework.stereotype.Component;

import javax.swing.plaf.ViewportUI;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @BelongsProject: createowl
 * @BelongsPackage: com.zt.createowl.util
 * @Author: zt
 * @CreateTime: 2023-02-28  15:59
 * @Description:
 */

@Component
public class CoreOWLUtil {

    /*
     * @Description: 返回OWL文件的资源开头名
     * @Author: zt
     * @Date: 2023/2/28 16:23
     * @param: []
     * @return: java.lang.String
     **/
    public static String getSourceName(){
        return "http://www.semanticweb.org/ztyt/ontologies/2023/2/fkfdcore";
    }

    /*
     * @Description: 返回OWL文件的命名空间(在开头名的基础上加上#号)
     * @Author: zt
     * @Date: 2023/2/28 16:26
     * @param: []
     * @return: java.lang.String
     **/
    public static String getNameSpace(){
        return  CoreOWLUtil.getSourceName() + "#";
    }

    /*
     * @Description: 将OntModel类的对象写成一个OWL文件
     * @Author: zt
     * @Date: 2023/2/28 16:37
     * @param: [ontModel]
     * @return: void
     **/
    public static void ontModel2Owl(OntModel ontModel) throws IOException {
        //输出owl文件到文件系统
        String filepath = "src/main/resources/owl/core/CoreOntology.owl";
        FileOutputStream fileOS = new FileOutputStream(filepath);
//        RDFWriter rdfWriter = baseOnt.getWriter("RDF/XML");
        RDFWriter rdfWriter = ontModel.getWriter("RDF/XML");
        rdfWriter.setProperty("showXMLDeclaration","true");
        rdfWriter.setProperty("showDoctypeDeclaration", "true");
        rdfWriter.write(ontModel, fileOS, null);
        //用writer就不需要用下面的方法了
        //baseOnt.write(fileOS, "RDF/XML");
        fileOS.close();
    }

    /*
     * @Description: 将指定位置的OWL文件读取为OntModel类的一个对象
     * @Author: zt
     * @Date: 2023/2/28 16:05
     * @param: []
     * @return: org.apache.jena.ontology.OntModel
     **/
    public static OntModel owl2OntModel(){
        //设置本体的命名空间
        String SOURCE = CoreOWLUtil.getSourceName();
        OntDocumentManager ontDocMgr = new OntDocumentManager();
        //设置本体owl文件的位置
        ontDocMgr.addAltEntry(SOURCE, "file:src/main/resources/owl/core/CoreOntology.owl");
        OntModelSpec ontModelSpec = new OntModelSpec(OntModelSpec.OWL_MEM);
        ontModelSpec.setDocumentManager(ontDocMgr);
        // asserted ontology
        OntModel baseOnt = ModelFactory.createOntologyModel(ontModelSpec);
        baseOnt.read(SOURCE, "RDF/XML");
        return baseOnt;
    }

    /*
     * @Description: 新增加一个类
     * @Author: zt
     * @Date: 2023/2/28 16:38
     * @param: [ontModel 读取OWL文件生成的OntModel类对象, className 新增加类的名称]
     * @return: org.apache.jena.ontology.OntClass
     **/
    public static OntClass createClass(OntModel ontModel, String className) throws IOException {
        String nameSpace = CoreOWLUtil.getNameSpace();
        OntClass newClass = ontModel.createClass(nameSpace + className);
        CoreOWLUtil.ontModel2Owl(ontModel);
        return newClass;
    }

    /*
     * @Description: 向传入的父类和子类添加父子关系
     * @Author: zt
     * @Date: 2023/2/28 16:53
     * @param: [ontModel 读取OWL文件生成的OntModel类对象, fatherClass 父类, sonClass 子类]
     * @return: void
     **/
    public static void addSubClass(OntModel ontModel, OntClass fatherClass, OntClass sonClass) throws IOException {
        fatherClass.addSubClass(sonClass);
        CoreOWLUtil.ontModel2Owl(ontModel);
    }

    /*
     * @Description: 向传入的头类和尾类之间添加关系，关系名称由参数传递
     * @Author: zt
     * @Date: 2023/2/28 17:11
     * @param: [ontModel 读取OWL文件生成的OntModel类对象, sourceClass 头类, targetClass 尾类, relationName 关系名称]
     * @return: org.apache.jena.ontology.ObjectProperty
     **/
    public static ObjectProperty addRelation(OntModel ontModel, OntClass sourceClass, OntClass targetClass, String relationName) throws IOException {
        String nameSpace = CoreOWLUtil.getNameSpace();
        ObjectProperty newRelation = ontModel.createObjectProperty(nameSpace + relationName);
        newRelation.addDomain(sourceClass);
        newRelation.addRange(targetClass);
        CoreOWLUtil.ontModel2Owl(ontModel);
        return newRelation;
    }

    /*
     * @Description: 向传入的类添加属性信息，属性名称由参数传递
     * @Author: zt
     * @Date: 2023/2/28 17:23
     * @param: [ontModel 读取OWL文件生成的OntModel类对象, ontClass 需要被添加的类别, propertyName 属性名称]
     * @return: org.apache.jena.ontology.DatatypeProperty
     **/
    public static DatatypeProperty addProperty(OntModel ontModel, OntClass ontClass, String propertyName) throws IOException {
        String nameSpace = CoreOWLUtil.getNameSpace();
        DatatypeProperty newProperty = ontModel.createDatatypeProperty(nameSpace + propertyName);
        newProperty.addDomain(ontClass);
        CoreOWLUtil.ontModel2Owl(ontModel);
        return newProperty;
    }

    /*
     * @Description: 删除指定的类中的属性信息
     * @Author: zt
     * @Date: 2023/3/27 15:39
     * @param: [ontModel, ontClass, propertyName]
     * @return: void
     **/
    public static void removeProperty(OntModel ontModel, OntClass ontClass, String propertyName) throws IOException {
        String nameSpace = CoreOWLUtil.getNameSpace();
        DatatypeProperty newProperty = ontModel.createDatatypeProperty(nameSpace + propertyName);
        newProperty.removeDomain(ontClass);
        CoreOWLUtil.ontModel2Owl(ontModel);
    }

    /*
     * @Description: 删除指定名称的类，如果这个类有子类的话，就不能删除，直接抛出异常
     * @Author: zt
     * @Date: 2023/2/28 18:18
     * @param: [ontModel 读取OWL文件生成的OntModel类对象, classname 需要删除的类的名称]
     * @return: void
     **/
    public static void removeClass(OntModel ontModel, String classname) throws Exception {
        OntClass ontClass = CoreOWLUtil.createClass(ontModel, classname);
        ontClass.remove();
        CoreOWLUtil.ontModel2Owl(ontModel);
    }

    /*
     * @Description: 删除指定名称的关系中的指定的domain和range
     * @Author: zt
     * @Date: 2023/3/7 10:42
     * @param: [ontModel, propertyName 关系的名称, ontClass 要从这个关系中移除的类]
     * @return: void
     **/
    public static void removeRelationDomainAndRange(OntModel ontModel, String propertyName, OntClass ontClass) throws IOException {
        String nameSpace = CoreOWLUtil.getNameSpace();
        ObjectProperty relation = ontModel.createObjectProperty(nameSpace + propertyName);
        //只要这个类是这个关系的range或者domain，就进行删除
        if(relation.hasDomain(ontClass)){
            relation.removeDomain(ontClass);
        }
        if(relation.hasRange(ontClass)){
            relation.removeRange(ontClass);
        }
        CoreOWLUtil.ontModel2Owl(ontModel);
    }

    public static void removeRelation(OntModel ontModel, String propertyName, OntClass domain, OntClass range) throws IOException {
        String nameSpace = CoreOWLUtil.getNameSpace();
        ObjectProperty relation = ontModel.createObjectProperty(nameSpace + propertyName);
        if(relation.hasDomain(domain)){
            relation.removeDomain(domain);
        }
        if(relation.hasRange(range)){
            relation.removeRange(range);
        }
        CoreOWLUtil.ontModel2Owl(ontModel);
    }

}
