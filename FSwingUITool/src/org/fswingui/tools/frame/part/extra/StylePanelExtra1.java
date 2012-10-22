/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.extra;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import org.fswingui.plaf.Style;
import org.fswingui.plaf.SubjectCriterion;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.config.FSUIConfig;
import org.fswingui.plaf.config.JsonConfig;
import org.fswingui.plaf.config.SubjectEntityConfig;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.BaseParameterCoding;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.part.base.StylePanel;
import org.fswingui.tools.frame.part.base.pub.TreeData;
import org.fswingui.tools.frame.part.base.stylepanel.StylePopMenu;
import org.fswingui.utilities.Utility;

/**
 *
 * @author cloud
 */
public class StylePanelExtra1 extends StylePanel {
    private CurrentData currentData;
    private transient TreeModel treeModel;
    private transient DefaultMutableTreeNode rootNode;
    public static final  String STYLE =UIEngine.STYLE;
    public static final  String SUBJECT =UIEngine.SUBJECT;

    public StylePanelExtra1(){}
    public StylePanelExtra1(CurrentData currentData){
        super();
        this.currentData=currentData;
        super.stylePop=new StylePopMenu(this,super.tree);
        super.stylePop.init();
        super.stylePop.subjectEntitys=currentData.getSubjectEntitys();
        super.init();
      
    }
    public CurrentData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(CurrentData currentData) {
        this.currentData = currentData;
    }
    
    public void produceSubjectStoreAndSubjectIndex(DefaultMutableTreeNode root) {
        
           
            Enumeration  rooten=root.children();//处理style
            while(rooten.hasMoreElements()){
                DefaultMutableTreeNode styleNode=(DefaultMutableTreeNode) rooten.nextElement();
                TreeData styleData=(TreeData) styleNode.getUserObject();
                Style style=(Style) styleData.userData;     
                   
                if(style!=null && style.getId()!=null && style.getMainColor()!=null){
                     
                     UIEngine.getConfig().getStyleMainColor().put(style.getId(),style.getMainColor());
                }
               
                   
                Enumeration  styleen=styleNode.children();
                while (styleen.hasMoreElements()){
                    DefaultMutableTreeNode subNode=(DefaultMutableTreeNode) styleen.nextElement();
                    TreeData subData=(TreeData) subNode.getUserObject();
                   
                    DefaultMutableTreeNode node1 =(DefaultMutableTreeNode) subNode.getFirstChild();
                    TreeData data1=(TreeData) node1.getUserObject();
                    if(data1.id.equals(StylePopMenu.SUBJECT_NODE)){
                        Enumeration  subeen=node1.children();
                        SubjectEntity se=null;
                        while (subeen.hasMoreElements()){
                            DefaultMutableTreeNode subeNode=(DefaultMutableTreeNode) subeen.nextElement();
                            TreeData subeData=(TreeData) subeNode.getUserObject();
                            
                            if(subeData.id.equals(SubjectCriterion.MAIN)){
                               SubjectEntity se1=(SubjectEntity) subeData.userData;
                               se=CurrentData.getSubjectEntitys().get(se1.getSubjectID());
                               if(se==null) se=se1;
                            } else {
                                SubjectEntity se1=(SubjectEntity) subeData.userData;
                                SubjectEntity se2=CurrentData.getSubjectEntitys().get(se1.getSubjectID());
                                if (se2==null) se2=se1;
                                se.put(subeData.id, se2);
                            }
                            
                        }
                        String key=styleData.id+"."+subData.id;
                        if(se!=null){
                             UIEngine.getConfig().getSubjectStore().put(key, se);
                        }else{
                            
                        }
                    } 
                   
                    node1 =(DefaultMutableTreeNode) subNode.getChildAt(1);
                    if (node1==null) continue;
                    data1=(TreeData) node1.getUserObject();
                    if(data1.id.equals(StylePopMenu.COMPONENT_NODE)){
                        Enumeration  comen=node1.children();
                        String comstr="";
                        while (comen.hasMoreElements()){
                            DefaultMutableTreeNode subeNode=(DefaultMutableTreeNode) comen.nextElement();
                            TreeData subeData=(TreeData) subeNode.getUserObject();
                           
                             UIEngine.getConfig().getSubjectIndex().put(subeData.title, subData.id);
                            
                        }
                        
                    }
                    
                }
                
            } 
        Logger log1=Logger.getGlobal();
        Logger log2=Logger.getLogger("java.awt.event.EventDispatchThread");
        log1.setLevel(Level.ALL);
        log2.setLevel(Level.ALL);
        log1.setLevel(Level.SEVERE);
        log2.setLevel(Level.SEVERE);
        FileHandler fh=null;
        try {
            fh=new FileHandler("e:/testlog%g.log");
        } catch (IOException ex) {
            Logger.getLogger(StylePanelExtra1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(StylePanelExtra1.class.getName()).log(Level.SEVERE, null, ex);
        }
        fh.setFormatter(new SimpleFormatter());
        log1.addHandler(fh);
//        log2.addHandler(fh); 
        String[] strs = null;
        try {
            URI uri = (ClassLoader.getSystemResource("")).toURI();
            
    //                JsonConfig.test1("ClassLoader;===getSystemResource 后======;"); 
                    log1.log(Level.SEVERE,"值为："+uri);
                    String ss=uri.getPath();
                   
                    strs=ss.split("/\\w*/\\w*/\\w*\\w*$");
//                    String[] strs=ss.split("/\\w*/\\w*/\\w*\\w*$");
        } catch (Exception ex) {
            log1.log(Level.SEVERE,null,ex);
//            log2.log(Level.SEVERE, null, ex);
//            log1.log(Level.SEVERE, "uri出错");
            Logger.getLogger(StylePanelExtra1.class.getName()).log(Level.SEVERE, null, ex);
        }
                  log1.log(Level.INFO,strs[0]+"/"+FSUIConfig.configName);
                  UIEngine.getConfig().writeConfig(strs[0]+"/"+FSUIConfig.configName);
                  
//                UIEngine.getConfig().writeConfig(FSUIConfig.configName);
    //            UIEngine.getConfig().writeConfig("c:/aa.json");

    //            UIEngine.getConfig().writeConfig();
            
    //           str=uri.getPath()+str.replaceAll("[.]", "/");
        
       

        
    }
    
  
    
    public void exportLFSet(){
       
        if (tree==null || tree.getModel()==null|| tree.getModel().getRoot()==null ) 
        {
            return;
        }
        treeModel=tree.getModel();
        rootNode=(DefaultMutableTreeNode) treeModel.getRoot();
        produceSubjectStoreAndSubjectIndex(rootNode);
         
        
    }
    
    
}
