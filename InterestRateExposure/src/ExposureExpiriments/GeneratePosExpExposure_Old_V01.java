package ExposureExpiriments;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import net.finmath.exception.CalculationException;
import net.finmath.marketdata.model.curves.DiscountCurve;
import net.finmath.marketdata.model.curves.ForwardCurve;
import net.finmath.montecarlo.RandomVariable;
import net.finmath.montecarlo.interestrate.LIBORMarketModel;
import net.finmath.montecarlo.interestrate.LIBORMarketModel.Measure;
import net.finmath.montecarlo.interestrate.LIBORMarketModelInterface;
import net.finmath.montecarlo.interestrate.LIBORModelMonteCarloSimulation;
import net.finmath.montecarlo.interestrate.LIBORModelMonteCarloSimulationInterface;
import net.finmath.montecarlo.interestrate.modelplugins.LIBORCorrelationModelExponentialDecay;
import net.finmath.montecarlo.interestrate.modelplugins.LIBORCovarianceModelFromVolatilityAndCorrelation;
import net.finmath.montecarlo.interestrate.modelplugins.LIBORVolatilityModelFromGivenMatrix;
import net.finmath.montecarlo.interestrate.products.AbstractLIBORMonteCarloProduct;
import net.finmath.montecarlo.interestrate.products.Swap;
import net.finmath.montecarlo.interestrate.products.SwapLeg;
import net.finmath.montecarlo.interestrate.products.components.AbstractNotional;
import net.finmath.montecarlo.interestrate.products.components.ExposureEstimator;
import net.finmath.montecarlo.interestrate.products.components.Notional;
import net.finmath.montecarlo.interestrate.products.indices.AbstractIndex;
import net.finmath.montecarlo.interestrate.products.indices.LIBORIndex;
import net.finmath.montecarlo.process.ProcessEulerScheme;
import net.finmath.stochastic.RandomVariableInterface;
import net.finmath.time.ScheduleGenerator;
import net.finmath.time.ScheduleInterface;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.businessdaycalendar.BusinessdayCalendarExcludingTARGETHolidays;
import net.finmath.time.businessdaycalendar.BusinessdayCalendarInterface;
import java.io.FileWriter;


/**
 * @author Christian Fries
 *
 */
public class GeneratePosExpExposure_Old_V01 {

	private final NumberFormat formatter6 = new DecimalFormat("0.000000", new DecimalFormatSymbols(new Locale("en")));
	private final DecimalFormat formatterGerman = new DecimalFormat("0.00000000", new DecimalFormatSymbols(Locale.GERMAN));
	private final String format = "not german";
	
	private static String CSVName = "ExposureData24052017_old.csv";
	private static FileWriter fileWriter = null;
	Boolean continueWriting = false;

	@Test
	public void generateExpectedPositiveExposure() throws CalculationException {
		/*
		 * Create a receiver swap (receive fix, pay float)	
		 */
		writeOnCSVInitialize(continueWriting);
		int generatedRows = 0;
		double forwardCurveLevel_start = 0.02, discountCurveLevel_start = 0.015;
		double forwardCurveLevel = forwardCurveLevel_start;
		double discountCurveLevel = discountCurveLevel_start;
		
		// loop over forward curve level
		for(int u = 0; u <= 0; u++){
			forwardCurveLevel += + 0.0025 * u;
			discountCurveLevel += - 0.005 * u;
			//loop over seed			
			for (int i = 1; i <= 1; i++){												// Seed from 1234 - 1244						
				int seed = 55233 + i;
				LIBORModelMonteCarloSimulationInterface lmm = createLIBORMarketModel(Measure.SPOT, 500, 5, 0.1, seed, forwardCurveLevel, discountCurveLevel);
				// loop over interest frequency
				for(ScheduleGenerator.Frequency frequency : Arrays.copyOfRange(ScheduleGenerator.Frequency.values(), 3, ScheduleGenerator.Frequency.values().length-1)){	// frequencies: MONTHLY, QUARTERLY, SEMIANNUAL, ANNUAL
					// loop over swap maturity
					for(int maturityNumber = 7; maturityNumber <= 7; maturityNumber++){					// maturity 1 - 15
						int yyyy = 2016 + maturityNumber;
						// int maturiyInYears = (yyyy - 2015);
						// print loop parameter
						System.out.println((u) + "\t"+ i + "\t"+ frequency + "\t"+ yyyy);
						
						ScheduleInterface legScheduleRec = ScheduleGenerator.createScheduleFromConventions(
								new LocalDate(2015, DateTimeConstants.JANUARY, 03) /* referenceDate */, 
								new LocalDate(2015, DateTimeConstants.JANUARY, 06) /* startDate */,
								new LocalDate(yyyy, DateTimeConstants.JANUARY, 06) /* maturityDate */,
								frequency /* frequency */,
								ScheduleGenerator.DaycountConvention.ACT_365 /* daycountConvention */,
								ScheduleGenerator.ShortPeriodConvention.FIRST /* shortPeriodConvention */,
								BusinessdayCalendarInterface.DateRollConvention.FOLLOWING /* dateRollConvention */,
								new BusinessdayCalendarExcludingTARGETHolidays() /* businessdayCalendar */,
								0 /* fixingOffsetDays */,
								0 /* paymentOffsetDays */);
				
						ScheduleInterface legSchedulePay = ScheduleGenerator.createScheduleFromConventions(
								new LocalDate(2015, DateTimeConstants.JANUARY, 03) /* referenceDate */, 
								new LocalDate(2015, DateTimeConstants.JANUARY, 06) /* startDate */,
								new LocalDate(yyyy, DateTimeConstants.JANUARY, 06) /* maturityDate */,
								frequency /* frequency */,
								ScheduleGenerator.DaycountConvention.ACT_365 /* daycountConvention */,
								ScheduleGenerator.ShortPeriodConvention.FIRST /* shortPeriodConvention */,
								BusinessdayCalendarInterface.DateRollConvention.FOLLOWING /* dateRollConvention */,
								new BusinessdayCalendarExcludingTARGETHolidays() /* businessdayCalendar */,
								0 /* fixingOffsetDays */,
								0 /* paymentOffsetDays */);
						
						AbstractNotional notional = new Notional(1.0);
						AbstractIndex index = new LIBORIndex("forwardCurve", 0.0, 0.25);
						
						//loop over fixedCoupon value
						for(int couponIndex = 0; couponIndex <= 6; couponIndex++){						//fixedCoupon 0.025 - 0.06 in 0.0025 steps
//							System.out.println("5 loop lap " + (couponIndex + 1));
							
							double fixedCoupon = forwardCurveLevel - 0.02 + 0.005 * couponIndex;   // warum darf coupon < forwardCurve nicht sein???  
					
							SwapLeg swapLegRec = new SwapLeg(legScheduleRec, notional, null, fixedCoupon /* spread */, false /* isNotionalExchanged */);
							SwapLeg swapLegPay = new SwapLeg(legSchedulePay, notional, index, 0.0 /* spread */, false /* isNotionalExchanged */);
							AbstractLIBORMonteCarloProduct swap = new Swap(swapLegRec, swapLegPay);
							AbstractLIBORMonteCarloProduct swapExposureEstimator = new ExposureEstimator(swap);
					
							// loop over swap periods (fix 120 periods!!!)
							for(double observationDate : lmm.getTimeDiscretization()) {
								
								if(observationDate == 0) continue;
								
								/*
								 * Calculate expected positive exposure of a swap
								 */
								RandomVariableInterface valuesSwap = swap.getValue(observationDate, lmm);
								RandomVariableInterface valuesEstimatedExposure = swapExposureEstimator.getValue(observationDate, lmm);
								RandomVariableInterface valuesPositiveExposure = valuesSwap.mult(valuesEstimatedExposure.barrier(valuesEstimatedExposure, new RandomVariable(1.0), 0.0));
					
								double exposureOnPath				= valuesEstimatedExposure.get(0);
								double expectedPositiveExposure		= valuesPositiveExposure.getAverage();
								
								// print  the values
								// System.out.print(formatterGerman.format(expectedPositiveExposure) + "\t");
	
								if(format == "german") writeOnCSV(formatterGerman.format(expectedPositiveExposure));								
								else writeOnCSV(Double.toString(expectedPositiveExposure));								
	
								writeOnCSVnextCell();							
								
	//							double basisPoint = 1E-4;
	//							Assert.assertTrue("Expected positive exposure", expectedPositiveExposure >= 0-1*basisPoint);			
							}
							
							// Parameter an die Reihen anhängen: Laufzeit, fixedCoupon
							writeOnCSV(Double.toString( (maturityNumber*2) + 1 ));
							writeOnCSVnextCell();		
							writeOnCSV(frequency.name());
							writeOnCSVnextCell();		
							writeOnCSV(Double.toString(fixedCoupon));
							writeOnCSVnextCell();		
							writeOnCSV(Double.toString(couponIndex));
							writeOnCSVnextLine();
							
							// visualize the generation process
							generatedRows++;
							if(generatedRows % 10 == 0){
								System.out.print("*** " + generatedRows + " done *** \n");
							}
								
						}
					}
				}
			}
		}
		writeOnCSVflushclose();
		System.out.print(CSVName);
		System.out.print("*** ready *** \n");
	}

	
	
	
	public static LIBORModelMonteCarloSimulationInterface createLIBORMarketModel(
			Measure measure, int numberOfPaths, int numberOfFactors, double correlationDecayParam, int seed
			,double forwardCurveLevel, double discountCurveLevel) throws CalculationException {

		/*
		 * Create the libor tenor structure and the initial values
		 */
		double liborPeriodLength	= 0.25;
		double liborRateTimeHorzion	= 31.0;			
		TimeDiscretization liborPeriodDiscretization = new TimeDiscretization(0.0, (int) (liborRateTimeHorzion / liborPeriodLength), liborPeriodLength);

		// Create the forward curve (initial value of the LIBOR market model)
		ForwardCurve forwardCurve = ForwardCurve.createForwardCurveFromForwards(
				"forwardCurve"								/* name of the curve */,
				new double[] {0.0 , 1.0 , 2.0 , 5.0 , 40.0}	/* fixings of the forward */,
				new double[] {forwardCurveLevel, forwardCurveLevel, forwardCurveLevel, forwardCurveLevel, forwardCurveLevel+0.05}	/* forwards */,
				liborPeriodLength							/* tenor / period length */
				);

		// Create the discount curve
		DiscountCurve discountCurve = DiscountCurve.createDiscountCurveFromZeroRates(
				"discountCurve"								/* name of the curve */,
				new double[] {0.5 , 1.0 , 2.0 , 5.0 , 40.0}	/* maturities */,
				new double[] {discountCurveLevel, discountCurveLevel, discountCurveLevel, discountCurveLevel, discountCurveLevel+0.05}	/* zero rates */
				);
		
		/*
		 * Create a simulation time discretization
		 */
		double lastTime	= 30.0;
		double dt		= 0.0625;

		TimeDiscretization timeDiscretization = new TimeDiscretization(0.0, (int) (lastTime / dt), dt);

		/*
		 * Create a volatility structure v[i][j] = sigma_j(t_i)
		 */
		double[][] volatility = new double[timeDiscretization.getNumberOfTimeSteps()][liborPeriodDiscretization.getNumberOfTimeSteps()];
		for (int timeIndex = 0; timeIndex < volatility.length; timeIndex++) {
			for (int liborIndex = 0; liborIndex < volatility[timeIndex].length; liborIndex++) {
				// Create a very simple volatility model here
				double time = timeDiscretization.getTime(timeIndex);
				double maturity = liborPeriodDiscretization.getTime(liborIndex);
				double timeToMaturity = maturity - time;

				double instVolatility;
				if(timeToMaturity <= 0)
					instVolatility = 0;				// This forward rate is already fixed, no volatility
				else
					instVolatility = 0.3 + 0.2 * Math.exp(-0.25 * timeToMaturity);

				// Store
				volatility[timeIndex][liborIndex] = instVolatility;
			}
		}
		LIBORVolatilityModelFromGivenMatrix volatilityModel = new LIBORVolatilityModelFromGivenMatrix(timeDiscretization, liborPeriodDiscretization, volatility);

		/*
		 * Create a correlation model rho_{i,j} = exp(-a * abs(T_i-T_j))
		 */
		LIBORCorrelationModelExponentialDecay correlationModel = new LIBORCorrelationModelExponentialDecay(
				timeDiscretization, liborPeriodDiscretization, numberOfFactors,
				correlationDecayParam);


		/*
		 * Combine volatility model and correlation model to a covariance model
		 */
		LIBORCovarianceModelFromVolatilityAndCorrelation covarianceModel =
				new LIBORCovarianceModelFromVolatilityAndCorrelation(timeDiscretization,
						liborPeriodDiscretization, volatilityModel, correlationModel);

		// BlendedLocalVolatlityModel (future extension)
		//		AbstractLIBORCovarianceModel covarianceModel2 = new BlendedLocalVolatlityModel(covarianceModel, 0.00, false);

		// Set model properties
		Map<String, String> properties = new HashMap<String, String>();

		// Choose the simulation measure
		properties.put("measure", measure.name());
		
		// Choose log normal model
		properties.put("stateSpace", LIBORMarketModel.StateSpace.LOGNORMAL.name());

		// Empty array of calibration items - hence, model will use given covariance
		LIBORMarketModel.CalibrationItem[] calibrationItems = new LIBORMarketModel.CalibrationItem[0];

		/*
		 * Create corresponding LIBOR Market Model
		 */
		LIBORMarketModelInterface liborMarketModel = new LIBORMarketModel(
				liborPeriodDiscretization, forwardCurve, discountCurve, covarianceModel, calibrationItems, properties);

		ProcessEulerScheme process = new ProcessEulerScheme(
				new net.finmath.montecarlo.BrownianMotion(timeDiscretization,
						numberOfFactors, numberOfPaths, seed /* seed */), ProcessEulerScheme.Scheme.PREDICTOR_CORRECTOR);

		return new LIBORModelMonteCarloSimulation(liborMarketModel, process);
	}
	
	public static void writeOnCSVInitialize(Boolean continueWriting){
		try {
			fileWriter = new FileWriter(CSVName, continueWriting);
		} catch (Exception e) {
	            System.out.println("Error in CsvFileWriter");
	            e.printStackTrace();
        }	
	}

	public static void writeOnCSV(String a){
		try {
			fileWriter.append(a);
		} catch (Exception e) {
	            System.out.println("Error in CsvFileWriter");
	            e.printStackTrace();
        }	
	}
	public static void writeOnCSVnextCell(){
		try {
			fileWriter.append(";");
		} catch (Exception e) {
	            System.out.println("Error in CsvFileWriter");
	            e.printStackTrace();
        }	
	}
	public static void writeOnCSVnextLine(){
		try {
			fileWriter.append("\n");
		} catch (Exception e) {
	            System.out.println("Error in CsvFileWriter");
	            e.printStackTrace();
        }	
	}
	public static void writeOnCSVflushclose(){
		try {
			fileWriter.flush();
            fileWriter.close();			
		} catch (Exception e) {
	            System.out.println("Error in CsvFileWriter");
	            e.printStackTrace();
        }	
	}
	
}
