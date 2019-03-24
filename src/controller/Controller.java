package controller;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import model.data_structures.IQueue;
import model.data_structures.IStack;
import model.vo.VODaylyStatistic;
import model.vo.VOMovingViolations;
import model.vo.VOViolationCode;
import view.MovingViolationsManagerView;

public class Controller {

	private MovingViolationsManagerView view;
	
	public final static String mesEnero = "./data/Moving_Violations_Issued_in_January_2018.csv";

	public final static String mesFebrero = "./data/Moving_Violations_Issued_in_February_2018.csv";

	public final static String mesMarzo = "./data/Moving_Violations_Issued_in_March_2018.csv";

	public final static String mesAbril = "./data/Moving_Violations_Issued_in_April_2018.csv";

	public final static String mesMayo = "./data/Moving_Violations_Issued_in_May_2018.csv";

	public final static String mesJunio = "./data/Moving_Violations_Issued_in_June_2018.csv";

	public final static String mesJulio = "./data/Moving_Violations_Issued_in_July_2018.csv";

	public final static String mesAgosto = "./data/Moving_Violations_Issued_in_August_2018.csv";

	public final static String mesSeptiembre = "./data/Moving_Violations_Issued_in_September_2018.csv";

	public final static String mesOctubre = "./data/Moving_Violations_Issued_in_October_2018.csv";

	public final static String mesNomviembre = "./data/Moving_Violations_Issued_in_November_2018.csv";

	public final static String mesdiciembre= "./data/Moving_Violations_Issued_in_December_2018.csv";
	
	public Double Xmin, Ymin, Xmax, Ymax;


	public Controller() {
		view = new MovingViolationsManagerView();
		
		Xmin=10.0;
		Ymin=10.0;
		
		Xmax=0.0;
		Ymax=0.0;
	}

	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean fin=false;
		Controller controller = new Controller();

		while(!fin)
		{
			view.printMenu();

			int option = sc.nextInt();

			switch(option)
			{
			case 0:
				view.printMessage("Ingrese el semestre (1 o 2)");
				int numeroCuatrimestre = sc.nextInt();
				int numCargados=controller.loadMovingViolations(numeroCuatrimestre);
				
				System.out.println("");
				System.out.println("El total de infracciones del semestre fue: "+numCargados);
				System.out.println("La zona geográfica Minimax es: ("+Xmin+","+Ymin+") y ("+Xmax+","+Ymax+")");
				break;

			case 1:
				view.printMessage("Ingrese el número N de franjas horarias deseadas");
				int N1 = sc.nextInt();
				controller.NFranjas();
				break;

			case 2:
				//Para las fechas
				/*view.printMessage("Ingrese la fecha con hora inicial (Ej : 28/03/2017T15:30:20)");
				LocalDateTime fechaInicialReq2A = convertirFecha_Hora_LDT(sc.next());

				view.printMessage("Ingrese la fecha con hora final (Ej : 28/03/2017T15:30:20)");
				LocalDateTime fechaFinalReq2A = convertirFecha_Hora_LDT(sc.next());

				IQueue<VOMovingViolations> resultados2 = controller.getMovingViolationsInRange(fechaInicialReq2A, fechaFinalReq2A);

				view.printMovingViolationsReq2(resultados2);*/
				
				
				break;

			case 3:

				view.printMessage("Ingrese el VIOLATIONCODE (Ej : T210)");
				String violationCode3 = sc.next();

				double [] promedios3 = controller.avgFineAmountByViolationCode(violationCode3);

				view.printMessage("FINEAMT promedio sin accidente: " + promedios3[0] + ", con accidente:" + promedios3[1]);
				break;


			case 4:

				view.printMessage("Ingrese el ADDRESS_ID");
				String addressId4 = sc.next();

				view.printMessage("Ingrese la fecha con hora inicial (Ej : 28/03/2017)");
				LocalDate fechaInicialReq4A = convertirFecha(sc.next());

				view.printMessage("Ingrese la fecha con hora final (Ej : 28/03/2017)");
				LocalDate fechaFinalReq4A = convertirFecha(sc.next());

				IStack<VOMovingViolations> resultados4 = controller.getMovingViolationsAtAddressInRange(addressId4, fechaInicialReq4A, fechaFinalReq4A);

				view.printMovingViolationsReq4(resultados4);

				break;

			case 5:
				view.printMessage("Ingrese el limite inferior de FINEAMT  (Ej: 50)");
				double limiteInf5 = sc.nextDouble();

				view.printMessage("Ingrese el limite superior de FINEAMT  (Ej: 50)");
				double limiteSup5 = sc.nextDouble();

				IQueue<VOViolationCode> violationCodes = controller.violationCodesByFineAmt(limiteInf5, limiteSup5);
				view.printViolationCodesReq5(violationCodes);
				break;

			case 6:

				view.printMessage("Ingrese el limite inferior de TOTALPAID (Ej: 200)");
				double limiteInf6 = sc.nextDouble();

				view.printMessage("Ingrese el limite superior de TOTALPAID (Ej: 200)");
				double limiteSup6 = sc.nextDouble();

				view.printMessage("Ordenar Ascendentmente: (Ej: true)");
				boolean ascendente6 = sc.nextBoolean();				

				IStack<VOMovingViolations> resultados6 = controller.getMovingViolationsByTotalPaid(limiteInf6, limiteSup6, ascendente6);
				view.printMovingViolationReq6(resultados6);
				break;

			case 7:

				view.printMessage("Ingrese la hora inicial (Ej: 23)");
				int horaInicial7 = sc.nextInt();

				view.printMessage("Ingrese la hora final (Ej: 23)");
				int horaFinal7 = sc.nextInt();

				IQueue<VOMovingViolations> resultados7 = controller.getMovingViolationsByHour(horaInicial7, horaFinal7);
				view.printMovingViolationsReq7(resultados7);
				break;

			case 8:

				view.printMessage("Ingrese el VIOLATIONCODE (Ej : T210)");
				String violationCode8 = sc.next();

				double [] resultado8 = controller.avgAndStdDevFineAmtOfMovingViolation(violationCode8);

				view.printMessage("FINEAMT promedio: " + resultado8[0] + ", desviación estandar:" + resultado8[1]);
				break;

			case 9:

				view.printMessage("Ingrese la hora inicial (Ej: 23)");
				int horaInicial9 = sc.nextInt();

				view.printMessage("Ingrese la hora final (Ej: 23)");
				int horaFinal9 = sc.nextInt();

				int resultado9 = controller.countMovingViolationsInHourRange(horaInicial9, horaFinal9);

				view.printMessage("Número de infracciones: " + resultado9);
				break;

			case 10:
				view.printMovingViolationsByHourReq10();
				break;

			case 13:
				view.printMessage("Ingrese la fecha inicial (Ej : 28/03/2017)");
				LocalDate fechaInicial11 = convertirFecha(sc.next());

				view.printMessage("Ingrese la fecha final (Ej : 28/03/2017)");
				LocalDate fechaFinal11 = convertirFecha(sc.next());

				double resultados11 = controller.totalDebt(fechaInicial11, fechaFinal11);
				view.printMessage("Deuda total "+ resultados11);
				break;

			case 12:	
				view.printTotalDebtbyMonthReq12();
				break;

			case 11:	
				fin=true;
				sc.close();
				break;
			}
		}

	}


	public int loadMovingViolations(int numeroSemestre) 
	{
		int numCargados=0;
		switch(numeroSemestre)
		{
		case 1:
			numCargados+= loadMovingViolationsXMes(mesEnero, false);
			numCargados+= loadMovingViolationsXMes(mesFebrero, false);
			numCargados+= loadMovingViolationsXMes(mesMarzo, false);
			numCargados+= loadMovingViolationsXMes(mesAbril, false);
			numCargados+= loadMovingViolationsXMes(mesMayo, false);
			numCargados+= loadMovingViolationsXMes(mesJunio, false);
			break;

		case 2:
			numCargados+= loadMovingViolationsXMes(mesJulio, false);
			numCargados+= loadMovingViolationsXMes(mesAgosto, false);
			numCargados+= loadMovingViolationsXMes(mesSeptiembre, false);
			numCargados+= loadMovingViolationsXMes(mesOctubre, true);
			numCargados+= loadMovingViolationsXMes(mesNomviembre, true);
			numCargados+= loadMovingViolationsXMes(mesdiciembre, true);					
			break;
		}
		return numCargados;
	}
	public int loadMovingViolationsXMes(String movingViolationsFile, boolean otroAtributo) {
		System.out.println("");
		System.out.println("Se está cargando: "+movingViolationsFile);
		int numCargados=0;
		int linea = 0;
		try {
			Reader reader = Files.newBufferedReader(Paths.get(movingViolationsFile));
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);


			for (CSVRecord csvRecord : csvParser) {
				linea++;
				if (linea == 1)
					continue;

				{
					// Accessing Values by Column Index
					String OBJECTID =  csvRecord.get(0);
					if(OBJECTID.contains(","))
						continue;
					int pOBJECTID=OBJECTID.equals("")?0:Integer.parseInt(OBJECTID);
					//String ROW_ = csvRecord.get(1);
					String LOCATION = csvRecord.get(2);
					
					String STREETSEGID = csvRecord.get(4);  
					String ADDRESS_ID = csvRecord.get(3).equals("")?STREETSEGID:csvRecord.get(3); 
					
					int pADDRESS_ID =ADDRESS_ID.equals("")?0:Integer.parseInt(ADDRESS_ID);
					int pSTREETSEGID=STREETSEGID.equals("")?0:Integer.parseInt(STREETSEGID);
					
					String XCOORD = csvRecord.get(5);
					String YCOORD = csvRecord.get(6);
					Double x = XCOORD.equals("")?0:Double.parseDouble(XCOORD);
					Double y = YCOORD.equals("")?0:Double.parseDouble(YCOORD);
					
					// TODO
					//Rectangulo Min y Max
					if(x>Xmax){Xmax=x;}
					if(y>Ymax){Ymax=y;}
					if(x<Xmin){Xmin=x;}
					if(y<Ymin){Ymin=y;}
					//String TICKETTYPE = csvRecord.get(7);
					
					String FINEAMT = csvRecord.get(8);
					String TOTALPAID = csvRecord.get(9);
					
					Double pFINEAMT=FINEAMT.equals("")?0:Double.parseDouble(FINEAMT);
					Double pTOTALPAID=TOTALPAID.equals("")?0:Double.parseDouble(TOTALPAID);
					/*String PENALTY1 = csvRecord.get(10);
					String PENALTY2 = csvRecord.get(11);*/					
					String ACCIDENTINDICATOR = csvRecord.get(12);
					boolean pACCIDENTINDICATOR=ACCIDENTINDICATOR.equalsIgnoreCase("Yes")?true:false;
					String TICKETISSUEDATE =  !otroAtributo ? csvRecord.get(13).toString() : csvRecord.get(14);
					String VIOLATIONDESC = !otroAtributo ? csvRecord.get(15).toString() :  csvRecord.get(16);
					String VIOLATIONCODE = !otroAtributo ? csvRecord.get(14).toString() :  csvRecord.get(15);
					//System.out.println(OBJECTID + "," + LOCATION +  "," + ADDRESS_ID + "," + STREETSEGID);
VOMovingViolations newMoving = new VOMovingViolations(pOBJECTID,LOCATION,pADDRESS_ID,pSTREETSEGID,pFINEAMT,pTOTALPAID,pACCIDENTINDICATOR,TICKETISSUEDATE,VIOLATIONCODE,VIOLATIONDESC, x, y);
					numCargados++;
//moving.add(listaa);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("AQUI - linea" + linea);
			System.out.println(e.getStackTrace().toString());
			System.out.println(e.getMessage());
		}
		System.out.println("El total de infracciones del mes fue: "+numCargados);
		return numCargados;
	}

	public IQueue <VODaylyStatistic> getDailyStatistics () {
		return null;
	}

	public IStack <VOMovingViolations> nLastAccidents(int n) {
		return null;
	}

	public void NFranjas() {
		// TODO
	}

	public IQueue<VOMovingViolations> getMovingViolationsInRange(LocalDateTime fechaInicial,
			LocalDateTime fechaFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	public double[] avgFineAmountByViolationCode(String violationCode3) {
		return new double [] {0.0 , 0.0};
	}

	public IStack<VOMovingViolations> getMovingViolationsAtAddressInRange(String addressId,
			LocalDate fechaInicial, LocalDate fechaFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	public IQueue<VOViolationCode> violationCodesByFineAmt(double limiteInf5, double limiteSup5) {
		// TODO Auto-generated method stub
		return null;
	}

	public IStack<VOMovingViolations> getMovingViolationsByTotalPaid(double limiteInf6, double limiteSup6,
			boolean ascendente6) {
		// TODO Auto-generated method stub
		return null;
	}

	public IQueue<VOMovingViolations> getMovingViolationsByHour(int horaInicial7, int horaFinal7) {
		// TODO Auto-generated method stub
		return null;
	}

	public double[] avgAndStdDevFineAmtOfMovingViolation(String violationCode8) {
		// TODO Auto-generated method stub
		return new double [] {0.0 , 0.0};
	}

	public int countMovingViolationsInHourRange(int horaInicial9, int horaFinal9) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double totalDebt(LocalDate fechaInicial11, LocalDate fechaFinal11) {
		// TODO Auto-generated method stub
		return 0;
	}


	/**
	 * Convertir fecha a un objeto LocalDate
	 * @param fecha fecha en formato dd/mm/aaaa con dd para dia, mm para mes y aaaa para agno
	 * @return objeto LD con fecha
	 */
	private static LocalDate convertirFecha(String fecha)
	{
		return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}


	/**
	 * Convertir fecha y hora a un objeto LocalDateTime
	 * @param fecha fecha en formato dd/mm/aaaaTHH:mm:ss con dd para dia, mm para mes y aaaa para agno, HH para hora, mm para minutos y ss para segundos
	 * @return objeto LDT con fecha y hora integrados
	 */
	private static LocalDateTime convertirFecha_Hora_LDT(String fechaHora)
	{
		return LocalDateTime.parse(fechaHora, DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss"));
	}
}
