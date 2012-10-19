/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.util.Arrays;  
import java.util.Collection;  
  
import org.junit.Assert;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.junit.runners.Parameterized;  
import org.junit.runners.Parameterized.Parameters;  
/** 
 * 参数化测试的类必须有Parameterized测试运行器修饰 
 * 
 */  
@RunWith(Parameterized.class)  
public class AddTest3 {  
    class Add{
        public int add(int a,int b){
            return a+b;
        }
    }

    private int input1;  
    private int input2;  
    private int expected;  
      
    /** 
     * 准备数据。数据的准备需要在一个方法中进行，该方法需要满足一定的要求： 
 
         1）该方法必须由Parameters注解修饰 
         2）该方法必须为public static的 
         3）该方法必须返回Collection类型 
         4）该方法的名字不做要求 
         5）该方法没有参数 
     * @return 
     */  
    @Parameters  
    @SuppressWarnings("unchecked")  
    public static Collection prepareData(){  
        Object [][] object = {{-1,-2,-3},{0,2,2},{-1,1,0},{1,2,3}};  
        return Arrays.asList(object);  
    }  
      
    public AddTest3(int input1,int input2,int expected){  
        this.input1 = input1;  
        this.input2 = input2;  
        this.expected = expected;  
    }  
    @Test  
    public void testAdd(){  
        Add add = new Add();  
        int result = add.add(input1, input2);  
        Assert.assertEquals(expected,result);  
    }  
      
} 