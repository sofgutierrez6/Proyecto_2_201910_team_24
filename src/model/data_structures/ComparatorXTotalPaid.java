package model.data_structures;

import java.util.Comparator;

import model.vo.VOMovingViolations;

public class ComparatorXTotalPaid implements Comparator<VOMovingViolations> {

	@Override
	public int compare(VOMovingViolations pV1, VOMovingViolations pV2) {
		
		int respuesta = 0;
		double multa1 = pV1.getTotalPaid();
		double multa2 = pV2.getTotalPaid();
		
		if(multa1 != multa2) {
			
			if(multa1 > multa2)
				respuesta = 1;
			else
				respuesta = -1;
		}
		
		return respuesta;
	}

}
