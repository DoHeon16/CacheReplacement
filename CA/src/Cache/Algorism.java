package Cache;

class Algorism{ // �˰��� Ŭ����

	private int count; // ���� ī��Ʈ, lfu �϶��� �� ī��Ʈ
	
	private int num;
	
	private int fPoint=1; // lfu���� ���� ����->fifo ����� ����(count�� ���� �� ���� ���� ����� ���� ��ü�ϱ� ����)
	
	
	
	public Algorism(Algorism[] a,int num){
	
		this.count=1;
	
		this.num=num;
	
	}
	
	public void countIncrease(){ // ī��Ʈ ������ ���� �޼ҵ�
	
	 
		this.count++;
	
	}
	
	
	
	 public void pointIncrease(){ // lfu ���� ī��Ʈ ������ ���� �޼ҵ�
	
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



