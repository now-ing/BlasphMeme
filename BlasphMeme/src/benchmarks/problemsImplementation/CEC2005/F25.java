package benchmarks.problemsImplementation.CEC2005;

public class F25 extends TestFunction {

	// Fixed (class) parameters
	static final public String FUNCTION_NAME = "Rotated Hybrid Composition Function 4 without bounds";
	static final public String DEFAULT_FILE_DATA = "supportData/hybrid_func4_data.txt";
	static final public String DEFAULT_FILE_MX_PREFIX = "supportData/hybrid_func4_M_D";
	static final public String DEFAULT_FILE_MX_SUFFIX = ".txt";

	// Number of functions
	static final public int NUM_FUNC = 10;

	private final MyHCJob theJob = new MyHCJob();

	// Shifted global optimum
	private final double[][] m_o;
	private final double[][][] m_M;
	private final double[] m_sigma = {
		2.0,	2.0,	2.0,	2.0,	2.0,
		2.0,	2.0,	2.0,	2.0,	2.0
	};
	private final double[] m_lambda = {
		10.0,		5.0/20.0,	1.0,	5.0/32.0,	1.0,
		5.0/100.0,	5.0/50.0,	1.0,	5.0/100.0,	5.0/100.0
	};
	private final double[] m_func_biases = {
		0.0,	100.0,	200.0,	300.0,	400.0,
		500.0,	600.0,	700.0,	800.0,	900.0
	};
	private final double[] m_testPoint;
	private final double[] m_testPointM;
	private final double[] m_fmax;

	// In order to avoid excessive memory allocation,
	// a fixed memory buffer is allocated for each function object.
	private double[] m_w;
	private double[][] m_z;
	private double[][] m_zM;

	// Constructors
	public F25 (int dimension, double bias) {
		this(dimension, bias, DEFAULT_FILE_DATA, DEFAULT_FILE_MX_PREFIX + dimension + DEFAULT_FILE_MX_SUFFIX);
	}
	public F25 (int dimension, double bias, String file_data, String file_m) {
		super(dimension, bias, FUNCTION_NAME);

		// Note: dimension starts from 0
		m_o = new double[NUM_FUNC][m_dimension];
		m_M = new double[NUM_FUNC][m_dimension][m_dimension];

		m_testPoint = new double[m_dimension];
		m_testPointM = new double[m_dimension];
		m_fmax = new double[NUM_FUNC];

		m_w = new double[NUM_FUNC];
		m_z = new double[NUM_FUNC][m_dimension];
		m_zM = new double[NUM_FUNC][m_dimension];

		// Load the shifted global optimum
		Benchmark.loadMatrixFromFile(file_data, NUM_FUNC, m_dimension, m_o);
		// Load the matrix
		Benchmark.loadNMatrixFromFile(file_m, NUM_FUNC, m_dimension, m_dimension, m_M);

		// Initialize the hybrid composition job object
		theJob.num_func = NUM_FUNC;
		theJob.num_dim = m_dimension;
		theJob.C = 2000.0;
		theJob.sigma = m_sigma;
		theJob.biases = m_func_biases;
		theJob.lambda = m_lambda;
		theJob.o = m_o;
		theJob.M = m_M;
		theJob.w = m_w;
		theJob.z = m_z;
		theJob.zM = m_zM;
		// Calculate/estimate the fmax for all the functions involved
		for (int i = 0 ; i < NUM_FUNC ; i ++) {
			for (int j = 0 ; j < m_dimension ; j ++) {
				m_testPoint[j] = (5.0 / m_lambda[i]);
			}
			Benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
			m_fmax[i] = Math.abs(theJob.basic_func(i, m_testPointM));
		}
		theJob.fmax = m_fmax;
	}

	private class MyHCJob extends HCJob {
		public double basic_func(int func_no, double[] x) {
			double result = 0.0;
			// This part is according to Matlab reference code
			switch(func_no) {
				case 0:
					result = Benchmark.weierstrass(x);
					break;
				case 1:
					result = Benchmark.EScafferF6(x);
					break;
				case 2:
					result = Benchmark.F8F2(x);
					break;
				case 3:
					result = Benchmark.ackley(x);
					break;
				case 4:
					result = Benchmark.rastrigin(x);
					break;
				case 5:
					result = Benchmark.griewank(x);
					break;
				case 6:
					result = Benchmark.EScafferF6NonCont(x);
					break;
				case 7:
					result = Benchmark.rastriginNonCont(x);
					break;
				case 8:
					result = Benchmark.elliptic(x);
					break;
				case 9:
					result = Benchmark.sphere_noise(x);
					break;
				default:
					System.err.println("func_no is out of range.");
					System.exit(-1);
			}
			return (result);
		}
	}

	// Function body
	public double f(double[] x) {

		double result = 0.0;

		result = Benchmark.hybrid_composition(x, theJob);

		result += m_bias;

		return (result);
	}
}
