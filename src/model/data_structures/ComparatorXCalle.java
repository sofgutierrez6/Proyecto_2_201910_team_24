package model.data_structures;

import java.util.Comparator;

import model.vo.VOMovingViolations;

public class ComparatorXCalle implements Comparator<VOMovingViolations> {

	@Override
	public int compare(VOMovingViolations pV1, VOMovingViolations pV2) {
		
		return pV1.getAddressId() - pV2.getAddressId();
	}

}
