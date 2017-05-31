package Exercise4;

import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.interestrate.LIBORModelMonteCarloSimulationInterface;
import net.finmath.montecarlo.interestrate.products.AbstractLIBORMonteCarloProduct;
import net.finmath.stochastic.RandomVariableInterface;

public class MyCaplet_Chris extends AbstractLIBORMonteCarloProduct {

		private final double	maturity;
		private final double	periodLength;
		private final double	strike;


		public MyCaplet_Chris(double maturity, double periodLength, double strike) {
			super();
			this.maturity = maturity;
			this.periodLength = periodLength;
			this.strike = strike;
		}

		@Override
		public RandomVariableInterface getValue(double evaluationTime, LIBORModelMonteCarloSimulationInterface model) throws CalculationException {        
			// This is on the LIBOR discretization
			double	paymentDate	= maturity+periodLength;
					
			// Get random variables
			RandomVariableInterface	libor					= model.getLIBOR(maturity, maturity, maturity+periodLength);
			RandomVariableInterface	numeraire				= model.getNumeraire(paymentDate);
		
			/*
			 * Calculate the payoff, which is
			 *    max(L-K,0) * periodLength         for caplet or
			 */
			RandomVariableInterface values = libor;		
			values = values.sub(strike).floor(0.0).mult(periodLength);

			values = values.div(numeraire);

			RandomVariableInterface	numeraireAtValuationTime				= model.getNumeraire(evaluationTime);		
			values = values.mult(numeraireAtValuationTime);

			return values;
		}
	
}
