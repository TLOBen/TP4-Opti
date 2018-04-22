/*********************************************
 * OPL 12.8.0.0 Model
 * Author: Julien Cusset, Benjamin Dagouret
 * Creation Date: 21 avr. 2018 at 16:23:03
 *********************************************/
 
 int nbMachines = ...;
 int nbJobs = ...;
 int p[1 .. nbMachines, 1 .. nbJobs] = ...;
 int s[1 .. nbMachines, 1 .. nbJobs, 1 .. nbJobs] = ...;
 int M = nbMachines * nbJobs * 100;
 
 // c[k,i]: completion time of job i on machine k
 dvar float c[1 .. nbMachines, 1 .. nbJobs];
 dvar float makespan;
 // b[i,j] boolean indicating if job i is before job j
 dvar boolean b[1 .. nbJobs, 1 .. nbJobs];
 
 execute{
	cplex.tilim=900; //15m
 }
 
 //add minimization on sum of c[k,i] so it begin as soon as possible
 minimize makespan;
			     
 subject to{
 	
 	// jobs are processed with machine natural order (machine 1, then machine 2, ...)
 	forall(i in 1 .. nbJobs, k in 2 .. nbMachines){
 		c[k,i] - c[k - 1,i] >= p[k - 1,i]; 	
 	} 
 	//------------------------------------------------------------------
 	
 	// non overlap within a machine
 	forall(k in 1 .. nbMachines, i in 1 .. nbJobs - 1, j in 2 .. nbJobs : j > i){
 		c[k,j] - c[k,i] + (1 - b[i,j]) * M >= s[k,i,j] + p[k,i];	
 	}
 	forall(k in 1 .. nbMachines, i in 1 .. nbJobs - 1, j in 2 .. nbJobs : j > i){
 		c[k,i] - c[k,j] + b[i,j] * M >= s[k,j,i] + p[k,j];	
 	}
 	//------------------------------------------------------------------
 	
 	// set makespan to max completion time
 	forall(k in 1 .. nbMachines, i in 1 .. nbJobs){
 		makespan >= c[k,i];
 	}	 
 	
 	// a job can be processed only by 1 machine at a time
 	forall(k in 1 .. nbMachines, i in 1 .. nbJobs){
 		c[k,i] >= 0; 	
 	}
 }
 
 execute {
 	writeln("makespan: " + makespan); 
 	write ("job order: ")
 	var n; 
 	
 	for(var i = 1; i <= nbJobs; i++){
 		for(var j = 1; j <= nbJobs; j++){
 			n = 0;		
 			for(var k = 1; k <= nbJobs; k++){
 				n += 1 - b[j][k];			
 			} 		
 			if(n == i){
 			 	write(j + " ");
  			} 			 			
 			
 		}
	} 	
 }