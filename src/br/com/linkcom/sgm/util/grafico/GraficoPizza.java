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
package br.com.linkcom.sgm.util.grafico;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.TableOrder;

import br.com.linkcom.sgm.beans.enumeration.GraficoTipoEnum;

public class GraficoPizza {

	public static JFreeChart criaGrafico(CategoryDataset dataset, String tituloGrafico, GraficoTipoEnum graficoTipo) {	
        final JFreeChart chart = ChartFactory.createMultiplePieChart(
                tituloGrafico,  		// chart title
                dataset,               	// dataset
                TableOrder.BY_ROW,
                true,                 	// include legend
                true,
                false
            );
        	chart.setBackgroundPaint(Color.white);
        	chart.getLegend().setBackgroundPaint(Color.lightGray);
            final MultiplePiePlot plot = (MultiplePiePlot) chart.getPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setOutlineStroke(new BasicStroke(1.0f));
            final JFreeChart subchart = plot.getPieChart();
            final PiePlot p = (PiePlot) subchart.getPlot();
            p.setBackgroundPaint(null);
            p.setOutlineStroke(null);
            
            if (graficoTipo.equals(GraficoTipoEnum.FAROL)) {//Farol
            	p.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
            	p.setSectionPaint(dataset.getColumnKey(0),Color.white);
            	p.setSectionPaint(dataset.getColumnKey(1),Color.red);
            	p.setSectionPaint(dataset.getColumnKey(2),Color.yellow);
            	p.setSectionPaint(dataset.getColumnKey(3),Color.green);
            	p.setSectionPaint(dataset.getColumnKey(4),Color.darkGray);
            }            
            
            //p.setMaximumLabelWidth(0.35);           
            p.setNoDataMessage("Sem dados cadastrados");            
            p.setLabelFont(new Font("Verdana", Font.PLAIN, 10));            
            p.setInteriorGap(0.19);           
            return chart;    
        }
}
