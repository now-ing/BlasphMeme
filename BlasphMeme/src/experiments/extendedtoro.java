/**
Copyright (c) 2018, Fabio Caraffini (fabio.caraffini@gmail.com, fabio.caraffini@dmu.ac.uk)
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the FreeBSD Project.
*/
package experiments;

import interfaces.Experiment;
import interfaces.Algorithm;
//import interfaces.Problem;
import algorithms.singleSolution.*;
//import algorithms.compact.*;
import benchmarks.CEC2014;


public class extendedtoro extends Experiment
{
	
	public extendedtoro(int probDim) throws Exception
	{
		//super(probDim,"DESIGN");
		super(probDim,5000,"DESIGN");
		setNrRuns(30);
		

		Algorithm a;// ///< A generic optimiser.
////	    Problem p;// ///< A generic problem.
		
//		//questo e' stato quello uufficializzato
//		a = new ResampledCMAES11();
//	    a.setParameter("p0",0.95); 
//	    a.setParameter("p1",0.18181818181); // 2/11
//	    a.setParameter("p2", 0.08333333333);// 1/12
//	    a.setParameter("p3", 0.44);
//	    a.setParameter("p4", 1.0);// 1 --> problem dependent!!
//	    a.setParameter("p5", 0.20);
//	    add(a);
	    

		a = new Powell();
		a.setParameter("p0", 0.0001);
		a.setParameter("p1", 100.0);
		add(a);
		
		a = new SolisWets();
		a.setParameter("p0", 1.0);
		add(a);
	    
//	    a = new cDE_exp_light();
//	    a.setParameter("p0",300.0);
//	    a.setParameter("p1", 0.25);
//	    a.setParameter("p2", 0.5);
//	    a.setParameter("p3", 3.0);
//	    a.setParameter("p4", 1.0);
//	    a.setParameter("p5", 1.0);
//	    add(a);
	
		for(int i = 1; i<=30; i++)
			add(new CEC2014(probDim, i));

	}
}
