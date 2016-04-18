/* 
		Copyright 2007,2008,2009,2010 da Linkcom Inform�tica Ltda
		
		Este arquivo � parte do programa GEPLANES.
 	   
 	    O GEPLANES � software livre; voc� pode redistribu�-lo e/ou 
		modific�-lo sob os termos da Licen�a P�blica Geral GNU, conforme
 	    publicada pela Free Software Foundation; tanto a vers�o 2 da 
		Licen�a como (a seu crit�rio) qualquer vers�o mais nova.
 	
 	    Este programa � distribu�do na expectativa de ser �til, mas SEM 
		QUALQUER GARANTIA; sem mesmo a garantia impl�cita de 
		COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM PARTICULAR. 
		Consulte a Licen�a P�blica Geral GNU para obter mais detalhes.
 	 
 	    Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU  	    
		junto com este programa; se n�o, escreva para a Free Software 
		Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
		02111-1307, USA.
		
*/
package br.com.linkcom.sgm.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Nomes {
	
	public static final String VERSAO = "3.0.4";
	
	public static final String PLANOS_DE_GESTAO = "ANOS DA GEST�O";
	public static final String PLANO_DE_GESTAO  = "ANO DA GEST�O";
	public static final String plano_de_gestao  = "ano da gest�o";
	public static final String Plano_de_gestao  = "Ano da gest�o";
	public static final String Plano_de_Gestao  = "Ano da Gest�o";
	
	public static final String VALORES_DE_BASE  = "METAS";
	public static final String valor_de_base    = "meta";
	public static final String valores_de_base  = "metas";
	public static final String Valores_de_Base  = "Metas";
	
	public static final String VALORES_REAIS  = "RESULTADOS";
	public static final String valores_reais  = "resultados";
	public static final String Valores_Reais  = "Resultados";
	
	public static final String ESTRATEGIAS = "OBJETIVOS ESTRAT�GICOS";
	public static final String ESTRATEGIA  = "OBJETIVO ESTRAT�GICO";
	public static final String Estrategias  = "Objetivos Estrat�gicos";
	public static final String Estrategia  = "Objetivo Estrat�gico";
	public static final String estrategia  = "objetivo estrat�gico";
	
	public static List<String[]> values(){
		List<String[]> r = new ArrayList<String[]>();
		Field[] fields = Nomes.class.getFields();
		for (Field f : fields) {
			String nome = f.getName();
			String valor = null;
			try {
				valor = (String) f.get(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			r.add(new String[] {nome, valor});
		}
		return r;
		
	}
}