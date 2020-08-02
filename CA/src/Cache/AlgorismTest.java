package Cache;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class AlgorismTest {

//	static int[] n_hit=new int[4];//�� �˰����� hit �� ����
	static long[] AlgTime=new long[4];
	//0-lru 1-lfu 2-fifo 3-random
	static Scanner input = new Scanner(System.in);
	static int overlap1=0,overlap2=0;
	
	public static void main(String[] args) {

		// TODO Auto-generated method stub

		int way = 0;
		
		while (way != 1 && way != 2) { // 1�� �Է��ϸ� ���� ��������, 2�� �Է��ϸ� �����Է� ����

			System.out.print("1. ���� �Է�\t2. ����� ���� �Է�\t==> ");

			way = input.nextInt();

			if (way != 1 && way != 2)

				System.out.println("�ٽ� �Է��Ͻʽÿ�");

		}
		System.out.print("ĳ�� ���� ��: ");

		int size = input.nextInt(); // ũ�⸦ �Է¹޾� �� �˰��� ũ�⸦ ����
		input.nextLine();
		
		Algorism[] lru = new Algorism[size];

		Algorism[] lfu = new Algorism[size];

		//Algorism[] fifo = new Algorism[size];

		Algorism[] random = new Algorism[size];
		

		System.out.print("�Է� ������ ��: ");
		
		int[] num = new int[input.nextInt()];
		String pattern="";
		if (way == 1) { // ���������� ����������

			//���� ����� �Է� ����
			for (int i = 0; i < num.length; i++) { // num���� ������ ����� num �迭�� ����

				num[i] = (int) (Math.random() * 1000)+1;
				//���� �߻� �� 1-1000������ ������ ����
			}
			
			//��Ģ���� ���� �߻�
//			Pattern2(num);

		}

		if (way == 2) { // �����Է��� ����������

			//���� ����� �Է� ��Ȳ
			System.out.println("���ڸ� " + num.length + "�� �Է��Ͻʽÿ�");
			for (int i = 0; i < num.length; i++) { // ���ڸ� num�� �Է¹޾� num �迭�� ����

				num[i] = input.nextInt();
			}
			
			
			//�ұ�Ģ�� ���� �Է� ��Ȳ
			//pattern=Pattern1(num,2,8);//////////////////////����� ���� ���� �� ����

		}
		System.out.println("----------------------------------------");

		System.out.println("LRU �˰���");

		System.out.println("");
		long time1=System.nanoTime();
		double lruHit = lruAlgorism(lru, num);
		long time2=System.nanoTime();
		//System.out.println("�˰��� ���� �ð� : "+(time2-time1)/1000.0+"ns");
		AlgTime[0]=(long) ((time2-time1)/1000.0);
		
		System.out.println();
		
		System.out.println("----------------------------------------");

		System.out.println("LFU �˰���");

		System.out.println("");
		time1=System.nanoTime();
		double lfuHit = lfuAlgorism(lfu, num);
		time2=System.nanoTime();
		//System.out.println("�˰��� ���� �ð� : "+(time2-time1)/1000.0+"ns");
		AlgTime[1]=(long) ((time2-time1)/1000.0);
		
		System.out.println();
		System.out.println("----------------------------------------");

		System.out.println("FIFO �˰���");

		System.out.println("");
		time1=System.nanoTime();
		double fifoHit = fifoAlgorism(num,size);
		time2=System.nanoTime();
		//System.out.println("�˰��� ���� �ð� : "+(time2-time1)/1000.0+"ns");
		AlgTime[2]=(long) ((time2-time1)/1000.0);
		
		System.out.println();
		System.out.println("----------------------------------------");

		System.out.println("RANDOM �˰���");

		System.out.println("");
		time1=System.nanoTime();
		double randomHit = randomAlgorism(random, num);
		time2=System.nanoTime();
		//System.out.println("�˰��� ���� �ð� : "+(time2-time1)/1000.0+"ns");
		AlgTime[3]=(long) ((time2-time1)/1000.0);
		

		System.out.println("");
		System.out.println("----------------------------------------");

		System.out.println();
		if(pattern!=null) {
			System.out.print("Pattern : ");
			System.out.println(pattern);
			System.out.println("\n1��° ������ ������ ��ġ���� "+overlap1+"��, 2��° ������ ������ ��ġ���� "+overlap2+"�� ��Ÿ�����ϴ�.\n");
			System.out.println("----------------------------------------\n");
		}
		
		System.out.println("LRU �˰��� ����ð� : "+AlgTime[0]+" ns");
		System.out.println("LFU �˰��� ����ð� : "+AlgTime[1]+" ns");
		System.out.println("FIFO �˰��� ����ð� : "+AlgTime[2]+" ns");
		System.out.println("RANDOM �˰��� ����ð� : "+AlgTime[3]+" ns");
		System.out.println();
		System.out.println("----------------------------------------");

		System.out.println();
		System.out.println("LRU �˰��� Hit ratio : " + lruHit);

		System.out.println("LFU �˰��� Hit ratio : " + lfuHit);

		System.out.println("FIFO �˰��� Hit ratio : " + fifoHit);

		System.out.println("RANDOM �˰��� Hit ratio : " + randomHit);

		System.out.println();
		hitRatio(lruHit, lfuHit, fifoHit, randomHit);
		
		input.close();
	}

	public static double lruAlgorism(Algorism[] lru, int num[]) { // LRU �˰���
	

		int hit = 0;

		int replacement = 0;

		int count = 0; // �迭�� null ���� �ִ����� Ȯ���ϱ� ���� ����

		for (int i = 0; i < num.length; i++) {

			System.out.println("����: " + num[i]);

			for (int j = 0; j < lru.length; j++) {

				if (lru[j] == null) { // ���� ���� ���� ������� ���

					increase(lru);

					lru[j] = new Algorism(lru, num[i]);

					count++;

					replacement++;

					j = lru.length;

					break;

				}

				else if (lru[j].getNum() == num[i]) { // ������ ������ �̹� ���� ���� ������� ��, Hit �������

					increase(lru);

					System.out.println("Hit!");

					lru[j].setCount(1); // ī��Ʈ�� �ٽ� 1�� ����� ��

					hit++;

					j = lru.length;

					break;

				}

				else if (count == lru.length && same(lru, num[i]) == 0) { // ĭ�� ��� ���ְ� ���� ���� �������

					increase(lru);

					lru[compare(lru)] = new Algorism(lru, num[i]);

					replacement++;

					j = lru.length;

				}

			}

			print(lru);

			System.out.println("");

			count(lru);

			System.out.println("");

		}
		System.out.println();
		System.out.println("Hit: " + hit + " Replacement: " + replacement);

		//n_hit[0]=hit;
		
		return Math.round(((double)hit/(double)num.length)*1000)/1000.0;

	}

	public static double lfuAlgorism(Algorism[] lfu, int num[]) {// LFU �˰���
		//long time1=System.nanoTime();

		int count = 0;// �迭�� null ���� �ִ����� Ȯ���ϱ� ���� ����

		int hit = 0;//��ü ���� ����

		int replacement = 0;//��ü �̽� ����

		for (int i = 0; i < num.length; i++) {

			System.out.println("����: " + num[i]);

			for (int j = 0; j < lfu.length; j++) {

				if (lfu[j] == null) { // ���� ���� ���� ������� ���,miss

					increasePoint(lfu);//��� ���� ���� count

					lfu[j] = new Algorism(lfu, num[i]);//lfu ���ο� ��ü ����(����)

					count++;

					replacement++;

					j = lfu.length;

					break;

				}

				else if (lfu[j].getNum() == num[i]) { // ������ ������ �̹� ���� ���� ������� ��, Hit �������

					increasePoint(lfu);

					System.out.println("Hit!");

					lfu[j].setFPoint(0); // ���������Ƿ� ���� ���� 0���� �ʱ�ȭ->FIFO ��� �� �� ī��Ʈ�� ���ƾ� ��ü���� ����

					lfu[j].countIncrease(); // ��� �� ����

					hit++;//���� Ƚ�� ����
					
					j = lfu.length;

					break;

				}

				else if (count == lfu.length && same(lfu, num[i]) == 0) { // ĭ�� ��� ���ְ� ���� ���� �������-->FIFO ���

					increasePoint(lfu);

					lfu[compare2(lfu)] = new Algorism(lfu, num[i]);

					replacement++;
					
					j = lfu.length;

				}

			}

			print(lfu);

			System.out.println("");

			count(lfu);

			System.out.println("");

		}
		System.out.println();
		System.out.println("Hit: " + hit + " Replacement: " + replacement);

		//n_hit[1]=hit;
		
//		long time2=System.nanoTime();
//		//System.out.println("�˰��� ���� �ð� : "+(time2-time1)/1000.0+"ns");
//		AlgTime[1]=(long) ((time2-time1)/1000.0);
//		
		return Math.round(((double)hit/num.length)*1000.0)/1000.0;

	}
	
	
	 static double fifoAlgorism(int[] num,int size) {// FIFO �˰���
	//		long time1=System.nanoTime();

	      int count=0;//hit count
	      
	      Queue<Integer> queue =new LinkedList<>();
	      for(int i=0; i<num.length;i++) {
	    	 System.out.println("���� : "+num[i]);
	         boolean s= false;
	         if(queue.size() <size) {
	            Iterator<Integer> it = queue.iterator();
	            while(it.hasNext()) {
	               if(it.next() == num[i]) {
	                  count++;
	                  System.out.println("Hit");
	                  s=true;
	                  break;
	               }   
	            }
	            if(s==false) {
	               queue.add(num[i]);
	               Iterator<Integer> it2 = queue.iterator();
	               while(it2.hasNext()) {
	                  System.out.print(it2.next()+" ");
	               }
	               System.out.println();
	            }
	               
	         }else if(queue.size() == size) {
	            Iterator<Integer> it = queue.iterator();
	            while(it.hasNext()) {
	               if(it.next() == num[i]) {
	                  count++;
	                  System.out.println("Hit");
	                  Iterator<Integer> it2 = queue.iterator();
	                  s=true;
	                  break;
	               }   
	            }
	            if(s==false) {
	               queue.remove();
	               queue.add(num[i]);
	               Iterator<Integer> it2 = queue.iterator();
	               while(it2.hasNext()) {
	                  System.out.print(it2.next()+" ");
	               }
	               System.out.println();
	            }
	            
	         } 
	         

	         }
	         System.out.println();

			System.out.println("Hit: " + count + " Replacement: " + (num.length-count));

	    //  n_hit[2]=count;
			
		//	long time2=System.nanoTime();
			//System.out.println("�˰��� ���� �ð� : "+(time2-time1)/1000.0+"ns");
	//		AlgTime[2]=(long) ((time2-time1)/1000.0);
			
			return Math.round(((double)count/num.length)*1000)/1000.0;
	      }	


	public static double randomAlgorism(Algorism[] random, int num[]) { // RANDOM �˰���
		//long time1=System.nanoTime();

		int hit = 0;

		int replacement = 0;

		for (int i = 0; i < num.length; i++) {

			System.out.println("����: " + num[i]);

			int r = (int) (Math.random() * random.length);

			for (int j = 0; j < random.length; j++) {

				if (same(random, num[i]) > 0) { // Hit �������

					increase(random);

					System.out.println("Hit!");

					hit++;

					j = random.length;

					break;

				}

				else { // Hit ���� ���� ���

					//�迭�� ������ r��°�� ���� ����

					replacement++;

					random[r] = new Algorism(random, num[i]);

					j = random.length;

				}

			}

			print(random);

			System.out.println("");

		}
		System.out.println();
		System.out.println("Hit: " + hit + " Replacement: " + replacement);

	//	n_hit[3]=hit;
		
	//	long time2=System.nanoTime();
		//System.out.println("�˰��� ���� �ð� : "+(time2-time1)/1000.0+"ns");
		//AlgTime[3]=(long) ((time2-time1)/1000.0);
		
		return Math.round(((double)hit/num.length)*1000)/1000.0;

	}

	public static void increase(Algorism[] a) { // ī��Ʈ ������ ���� �޼ҵ�

		for (int i = 0; i < a.length; i++) {

			if (a[i] != null)

				a[i].countIncrease();

		}

	}

	public static void increasePoint(Algorism[] a) { // ����Ʈ ������ ���� �޼ҵ�

		for (int i = 0; i < a.length; i++) {

			if (a[i] != null)

				a[i].pointIncrease();

		}

	}

	public static void print(Algorism[] a) { // �迭�� �־��� �ִ� ���� ����� ���� �޼ҵ�

		for (int i = 0; i < a.length; i++) {

			if (a[i] != null)

				System.out.print(a[i].getNum() + " ");

			else

				System.out.print("x ");

		}

	}

	public static void count(Algorism[] a) { // ���� ī��Ʈ�� Ȯ���ϱ� ���� �޼ҵ�

		System.out.print("ī��Ʈ : ");

		for (int i = 0; i < a.length; i++) {

			if (a[i] != null)

				System.out.print(a[i].getCount() + " ");

			if (a[i] == null)

				System.out.print(0 + " ");

		}
		System.out.println();
	}

	public static int same(Algorism[] a, int num) { // �迭�ȿ� ���� ���� ���ڿ� ��ġ�ϴ� ���ڰ� �ִ��� �˻��ϴ� �޼ҵ�

		int sameCount = 0;

		for (int i = 0; i < a.length; i++) {

			if (a[i] == null)

				continue;

			else if (a[i].getNum() == num)

				sameCount++;

		}

		return sameCount;

	}

	public static int compare(Algorism[] a) { // ��ü�� ���� ī��Ʈ�� ���� ���� �� �� ã���ִ� �޼ҵ�

		int compareNum = a[0].getCount();

		int compare = 0;

		for (int i = 0; i < a.length; i++) {

			if (compareNum < a[i].getCount()) {

				compareNum = a[i].getCount();

				compare = i;

			}

		}

		return compare;

	}

	public static int compare2(Algorism[] a) { // LFU�˰��򿡼� ���󵵰� ������ �߿��� ���簡 ���� ������ ���� ã���ִ� �޼ҵ�

		int compareNum = a[0].getCount();

		int compare = 0;//��ü �� �� ��° ���Կ��� ��ü�� �Ͼ���� index Ȯ��

		int comparePoint = a[0].getFPoint();

		for (int i = 0; i < a.length; i++) {
			
			if (compareNum > a[i].getCount()) {
		
				compareNum = a[i].getCount();
				comparePoint = a[i].getFPoint();
				compare = i;
			
			} else if (compareNum == a[i].getCount() && comparePoint < a[i].getFPoint()) {
				//�󵵼��� ���� �� FIFO ������� ��ü(fpoint�� �������� ���� �ִ� ����)
				
				comparePoint = a[i].getFPoint();
				compare = i;
			}
		}

		return compare;

	}

	public static void hitRatio(double lru, double lfu, double fifo, double random) { // 4���� �˰� ���� Hit Ratio �� ���Ͽ� ������ ���
		System.out.print("���߷��� ���� ���� ����");
		if (lru >= lfu && lru >= fifo && lru >= random)
			System.out.print(" LRU");
		if (lfu >= lru && lfu >= fifo && lfu >= random)
			System.out.print(" LFU");
		if (fifo >= lru && fifo >= lfu && fifo >= random)
			System.out.print(" FIFO");
		if (random >= lru && random >= lfu && random >= fifo)
			System.out.print(" RANDOM");
		System.out.println(" �Դϴ�.");
	}
	
	public static String Pattern1(int[] num, int length1,int length2) {//��Ģ������ ���� ����
		int array1[]=new int[length1];
		boolean ch=true;
		int array2[]=new int[length2];
		String pattern="";
		
		for(int i=0;i<length1;i++) {
			array1[i]=(int) (Math.random()*30+1);
			pattern+=String.valueOf(array1[i]+" ");
		}
		
		int a;
		pattern+=" / ";
		
		for(int i=0;i<length2;i++) {
			a=(int) (Math.random()*30+1);
			for(int s:array1) {
				if(a==s) {
					a=(int) (Math.random()*30+1);
					continue;
				}
			}
			array2[i]=a;
			pattern+=String.valueOf(array2[i]+" ");
		}
		
		for(int i=0;i<num.length;i++) {
			
			num[i]=(int) (Math.random()*30+1);
			
			
			if(length1!=0&&num[i]==array1[0]) {//���� 1�� ¦����° ��ġ�� �� ����
				if(ch) {
					for(int j=1;j<length1;j++) {
						if(i+j<num.length)
							num[i+j]=array1[j];
					}
					i+=length1;
					ch=!ch;	
					overlap1++;
				}
				else {
					ch=!ch;
				}					
			}else if(length2!=0&&num[i]==array2[0]) {//���� 2�� Ȧ����° ��ġ�� �� ����
				if(ch) {
					for(int j=1;j<length2;j++) {
						if(i+j<num.length)		
							num[i+j]=array2[j];
					}
					i+=length2;
					ch=!ch;
					overlap2++;
				}
				else {
					ch=!ch;
				}					
			}
			
				
		}
		
		return pattern;//pattern ��ȯ
	}
	
	 public static void Pattern2(int data[]) {//��Ģ���� ����
	      System.out.print("�ݺ� ���� : ");
	      Random r= new Random();
	      int re=input.nextInt();
	      System.out.print("������ � �ݺ�?");
	      int rearr = input.nextInt();
	      input.nextLine();
	      System.out.println("������ ���� �Է� : ");
	      int[] arr1= new int[rearr];
	      for(int i=0;i<rearr;i++) {
	         arr1[i]=input.nextInt();
	      }
	      
	      
	      for(int i=0; i<data.length;i++) {
	         int num =i%re;
	         
	         if(num <rearr) {
	            data[i]=arr1[num];
	         }else {
	            boolean t= false;
	           while(true) {
	              data[i]= r.nextInt(30);
	              int j;
	              for(j=0;j<arr1.length;j++) {
	                   if(data[i]==arr1[j]) {
	                     break;
	                   }    
	                }
	              if(j==arr1.length) {
	                 t=true;
	              }
	              if(t) {
	                 break;
	              }
	              
	           }
	             
	            
	            
	         }
	         
//	         switch (num) {
//	      case 0:
//	         data[i] = 1;
//	         break;
	//
//	      case 1:
//	         data[i] = 2;
//	         break;
	//
//	      case 2:
//	         data[i] = 3;
//	         break;
//	      default:
//	         data[i] = r.nextInt(30)+4;
//	         break;
//	      }
	         
	         
	      }
	      
	      
	   }

		
}
