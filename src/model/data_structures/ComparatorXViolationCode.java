package model.data_structures;

import java.util.Comparator;

import model.vo.VOMovingViolations;

public class ComparatorXViolationCode implements Comparator<VOMovingViolations> {

	@Override
	public int compare(VOMovingViolations pV1, VOMovingViolations pV2) {
		
		String direccion1 = pV1.getViolationCode();
		String direccion2 = pV2.getViolationCode();
		
		return direccion1.compareTo(direccion2);
	}

}