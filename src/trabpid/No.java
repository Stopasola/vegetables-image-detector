/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabpid;

import static java.lang.Integer.compare;
import java.util.Collections;

/**
 *
 * @author Fernando-
 */
public class No{
    
    public double Correlacao;
    public String Classes;

    public double getCorrelacao() {
        return Correlacao;
    }

    public void setCorrelacao(double Correlacao) {
        this.Correlacao = Correlacao;
    }

    public String getClasses() {
        return Classes;
    }

    public void setClasses(String Classes) {
        this.Classes = Classes;
    }
    
    @Override
    public String toString() {
        return "No{" +
                "Classes='" + Classes + '\'' +
                ", Correlacao=" + Correlacao +
                '}';
    }

}
