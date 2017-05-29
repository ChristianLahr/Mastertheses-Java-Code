package LMMPriceGeneration;

/*
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christianfries.com.
 *
 * Created on 06.11.2015
 */

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import net.finmath.exception.CalculationException;
import net.finmath.marketdata.model.AnalyticModel;
import net.finmath.marketdata.model.AnalyticModelInterface;
import net.finmath.marketdata.model.curves.Curve;
import net.finmath.marketdata.model.curves.CurveInterface;
import net.finmath.marketdata.model.curves.DiscountCurveInterface;
import net.finmath.marketdata.model.curves.ForwardCurve;
import net.finmath.marketdata.model.curves.ForwardCurveInterface;
import net.finmath.montecarlo.interestrate.LIBORMarketModel;
import net.finmath.montecarlo.interestrate.LIBORMarketModel.Measure;
import net.finmath.montecarlo.interestrate.LIBORMarketModelInterface;
import net.finmath.montecarlo.interestrate.LIBORModelMonteCarloSimulation;
import net.finmath.montecarlo.interestrate.LIBORModelMonteCarloSimulationInterface;
import net.finmath.montecarlo.interestrate.modelplugins.LIBORCorrelationModelExponentialDecay;
import net.finmath.montecarlo.interestrate.modelplugins.LIBORCovarianceModelFromVolatilityAndCorrelation;
import net.finmath.montecarlo.interestrate.modelplugins.LIBORVolatilityModelFromGivenMatrix;
import net.finmath.montecarlo.interestrate.products.AbstractLIBORMonteCarloProduct;
import net.finmath.montecarlo.interestrate.products.SimpleCappedFlooredFloatingRateBond;
import net.finmath.montecarlo.process.ProcessEulerScheme;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.businessdaycalendar.BusinessdayCalendarExcludingTARGETHolidays;
import net.finmath.time.businessdaycalendar.BusinessdayCalendarInterface;

/**
 * @author Christian Fries
 */
@RunWith(Parameterized.class)
public class ExtractLiborPaths {

	@Parameters
	public static Collection<Object[]> generateData()
	{
		return Arrays.asList(new Object[][] {
				{ Measure.SPOT }, { Measure.TERMINAL }
		});
	};

	private final int numberOfPaths = 1000;

	private final Measure measure;

	public ExtractLiborPaths(Measure measure) throws CalculationException {
		// Store measure
		this.measure = measure;
	}

	@Test
	public void test() throws CalculationException {

		/*
		 * Create Monte-Carlo model
		 */
		LIBORModelMonteCarloSimulationInterface model = createLIBORMarketModel(numberOfPaths, measure);
		
		/*
		 * Create Product
		 */
		int numberOfTimeSteps = 19;
		double[] fixingDates  = (new TimeDiscretization(0.0, numberOfTimeSteps, 0.5)).getAsDoubleArray();
		double[] paymentDates = (new TimeDiscretization(0.5, numberOfTimeSteps, 0.5)).getAsDoubleArray();
		double maturity = 0.5 + numberOfTimeSteps * 0.5;;

		double[] floors = null;
		double[] caps = null;
		double[] spreads = null;

		AbstractLIBORMonteCarloProduct product = new SimpleCappedFlooredFloatingRateBond("", fixingDates, paymentDates, spreads, floors, caps, maturity);

		
		
//#############################		
		DecimalFormat formatterGerman = new DecimalFormat("0.00000000", new DecimalFormatSymbols(Locale.GERMAN));
		DecimalFormat formatterGermanShort = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.GERMAN));
		double timeToEvaluate = 2;
		double value = product.getValue(model);
		System.out.println("###################");
		System.out.println("Value of the product at ... as Random Variable \n " + product.getValue(timeToEvaluate, model).toString());
		System.out.println("Max:           " + product.getValue(timeToEvaluate, model).getQuantile(0));
		System.out.println("Min:           " + product.getValue(timeToEvaluate, model).getQuantile(1));
		System.out.println("Average:       " + product.getValue(timeToEvaluate, model).getAverage());
	
		System.out.println("95 % Quantiles: ");
		System.out.println(	"Time   quantile       average");

		for(double time = 0; time <= 20; time++){
			System.out.println(	formatterGermanShort.format(time*0.5) + "    " + 
								formatterGerman.format(product.getValue(time*0.5, model).getQuantile(0.95)) + "     "  + 
								formatterGerman.format(product.getValue(time*0.5, model).getAverage()));
		}
//#############################		

		
		
		System.out.println("Value of floating rate bond (measure = " + measure + "): " + value);

		if(measure == Measure.SPOT)		Assert.assertEquals("Value of floating rate bond.", 1.0, value, 1E-10);
		if(measure == Measure.TERMINAL)	Assert.assertEquals("Value of floating rate bond.", 1.0, value, 2E-2);
	}

	public static LIBORModelMonteCarloSimulationInterface createLIBORMarketModel(int numberOfPaths, Measure measure) throws CalculationException {

		LocalDate	referenceDate = new LocalDate(2014, DateTimeConstants.AUGUST, 12);

		// Create the forward curve (initial value of the LIBOR market model)
		ForwardCurveInterface forwardCurve = ForwardCurve.createForwardCurveFromForwards(
				"forwardCurve"								/* name of the curve */,
				referenceDate,
				"6M",
				new BusinessdayCalendarExcludingTARGETHolidays(),
				BusinessdayCalendarInterface.DateRollConvention.FOLLOWING,
				Curve.InterpolationMethod.LINEAR,
				Curve.ExtrapolationMethod.CONSTANT,
				Curve.InterpolationEntity.VALUE,
				ForwardCurve.InterpolationEntityForward.FORWARD,
				null,
				null,
				new double[] {0.5 , 1.0 , 2.0 , 5.0 , 40.0}	/* fixings of the forward */,
				new double[] {0.05, 0.05, 0.05, 0.05, 0.05}	/* forwards */
				);

		// No discount curve - single curve model
		DiscountCurveInterface discountCurve = null;

		//		AnalyticModelInterface model = new AnalyticModel(new CurveInterface[] { forwardCurve , discountCurve });
		AnalyticModelInterface model = new AnalyticModel(new CurveInterface[] { forwardCurve });

		/*
		 * Create the libor tenor structure and the initial values
		 */
		double liborPeriodLength	= 0.5;
		double liborRateTimeHorzion	= 40.0;
		TimeDiscretization liborPeriodDiscretization = new TimeDiscretization(0.0, (int) (liborRateTimeHorzion / liborPeriodLength), liborPeriodLength);		

		/*
		 * Create a simulation time discretization
		 */
		double lastTime	= 40.0;
		double dt		= 0.5;

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
		int numberOfFactors = 5;
		double correlationDecayParam = 0.2;
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
				liborPeriodDiscretization, model, forwardCurve, discountCurve, covarianceModel, calibrationItems, properties);

		ProcessEulerScheme process = new ProcessEulerScheme(
				new net.finmath.montecarlo.BrownianMotion(timeDiscretization,
						numberOfFactors, numberOfPaths, 3141 /* seed */), ProcessEulerScheme.Scheme.PREDICTOR_CORRECTOR);

		return new LIBORModelMonteCarloSimulation(liborMarketModel, process);
	}
}