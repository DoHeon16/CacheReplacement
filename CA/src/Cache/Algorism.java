package Cache;

class Algorism{ // 알고리즘 클래스

	private int count; // 적재 카운트, lfu 일때는 빈도 카운트
	
	private int num;
	
	private int fPoint=1; // lfu에서 적재 순서->fifo 사용을 위함(count가 같을 때 가장 먼저 적재된 것을 교체하기 위해)
	
	
	
	public Algorism(Algorism[] a,int num){
	
		this.count=1;
	
		this.num=num;
	
	}
	
	public void countIncrease(){ // 카운트 증가를 위한 메소드
	
	 
		this.count++;
	
	}
	
	
	
	 public void pointIncrease(){ // lfu 적재 카운트 증가를 위한 메소드
	
		 this.fPoint++;
	
	}
	
	 public void setCount(int count){
	
		 this.count=count;
	
	}
	
	 public void setFPoint(int fPoint){
	
		 this.fPoint=fPoint;
	
	}
	
	public int getCount(){
	
		return count;
	
	}
	
	public int getFPoint(){
	
		return fPoint;
	
	}
	
	public int getNum(){
	
		return num;
	
	}
	
 
}



