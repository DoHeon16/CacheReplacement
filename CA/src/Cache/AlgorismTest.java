package Cache;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class AlgorismTest {

//	static int[] n_hit=new int[4];//각 알고리즘의 hit 수 저장
	static long[] AlgTime=new long[4];
	//0-lru 1-lfu 2-fifo 3-random
	static Scanner input = new Scanner(System.in);
	static int overlap1=0,overlap2=0;
	
	public static void main(String[] args) {

		// TODO Auto-generated method stub

		int way = 0;
		
		while (way != 1 && way != 2) { // 1을 입력하면 숫자 랜덤생성, 2를 입력하면 직접입력 받음

			System.out.print("1. 랜덤 입력\t2. 사용자 직접 입력\t==> ");

			way = input.nextInt();

			if (way != 1 && way != 2)

				System.out.println("다시 입력하십시오");

		}
		System.out.print("캐시 라인 수: ");

		int size = input.nextInt(); // 크기를 입력받아 각 알고리즘에 크기를 넣음
		input.nextLine();
		
		Algorism[] lru = new Algorism[size];

		Algorism[] lfu = new Algorism[size];

		//Algorism[] fifo = new Algorism[size];

		Algorism[] random = new Algorism[size];
		

		System.out.print("입력 데이터 수: ");
		
		int[] num = new int[input.nextInt()];
		String pattern="";
		if (way == 1) { // 랜덤생성을 선택했을때

			//실제 사용자 입력 사항
			for (int i = 0; i < num.length; i++) { // num개의 난수를 만들어 num 배열에 저장

				num[i] = (int) (Math.random() * 1000)+1;
				//난수 발생 시 1-1000까지의 범위로 설정
			}
			
			//규칙적인 패턴 발생
//			Pattern2(num);

		}

		if (way == 2) { // 직접입력을 선택했을때

			//실제 사용자 입력 상황
			System.out.println("숫자를 " + num.length + "번 입력하십시오");
			for (int i = 0; i < num.length; i++) { // 숫자를 num번 입력받아 num 배열에 저장

				num[i] = input.nextInt();
			}
			
			
			//불규칙한 패턴 입력 상황
			//pattern=Pattern1(num,2,8);//////////////////////사용자 임의 패턴 내 개수

		}
		System.out.println("----------------------------------------");

		System.out.println("LRU 알고리즘");

		System.out.println("");
		long time1=System.nanoTime();
		double lruHit = lruAlgorism(lru, num);
		long time2=System.nanoTime();
		//System.out.println("알고리즘 수행 시간 : "+(time2-time1)/1000.0+"ns");
		AlgTime[0]=(long) ((time2-time1)/1000.0);
		
		System.out.println();
		
		System.out.println("----------------------------------------");

		System.out.println("LFU 알고리즘");

		System.out.println("");
		time1=System.nanoTime();
		double lfuHit = lfuAlgorism(lfu, num);
		time2=System.nanoTime();
		//System.out.println("알고리즘 수행 시간 : "+(time2-time1)/1000.0+"ns");
		AlgTime[1]=(long) ((time2-time1)/1000.0);
		
		System.out.println();
		System.out.println("----------------------------------------");

		System.out.println("FIFO 알고리즘");

		System.out.println("");
		time1=System.nanoTime();
		double fifoHit = fifoAlgorism(num,size);
		time2=System.nanoTime();
		//System.out.println("알고리즘 수행 시간 : "+(time2-time1)/1000.0+"ns");
		AlgTime[2]=(long) ((time2-time1)/1000.0);
		
		System.out.println();
		System.out.println("----------------------------------------");

		System.out.println("RANDOM 알고리즘");

		System.out.println("");
		time1=System.nanoTime();
		double randomHit = randomAlgorism(random, num);
		time2=System.nanoTime();
		//System.out.println("알고리즘 수행 시간 : "+(time2-time1)/1000.0+"ns");
		AlgTime[3]=(long) ((time2-time1)/1000.0);
		

		System.out.println("");
		System.out.println("----------------------------------------");

		System.out.println();
		if(pattern!=null) {
			System.out.print("Pattern : ");
			System.out.println(pattern);
			System.out.println("\n1번째 패턴은 임의의 위치에서 "+overlap1+"번, 2번째 패턴은 임의의 위치에서 "+overlap2+"번 나타났습니다.\n");
			System.out.println("----------------------------------------\n");
		}
		
		System.out.println("LRU 알고리즘 수행시간 : "+AlgTime[0]+" ns");
		System.out.println("LFU 알고리즘 수행시간 : "+AlgTime[1]+" ns");
		System.out.println("FIFO 알고리즘 수행시간 : "+AlgTime[2]+" ns");
		System.out.println("RANDOM 알고리즘 수행시간 : "+AlgTime[3]+" ns");
		System.out.println();
		System.out.println("----------------------------------------");

		System.out.println();
		System.out.println("LRU 알고리즘 Hit ratio : " + lruHit);

		System.out.println("LFU 알고리즘 Hit ratio : " + lfuHit);

		System.out.println("FIFO 알고리즘 Hit ratio : " + fifoHit);

		System.out.println("RANDOM 알고리즘 Hit ratio : " + randomHit);

		System.out.println();
		hitRatio(lruHit, lfuHit, fifoHit, randomHit);
		
		input.close();
	}

	public static double lruAlgorism(Algorism[] lru, int num[]) { // LRU 알고리즘
	

		int hit = 0;

		int replacement = 0;

		int count = 0; // 배열에 null 값이 있는지를 확인하기 위한 변수

		for (int i = 0; i < num.length; i++) {

			System.out.println("숫자: " + num[i]);

			for (int j = 0; j < lru.length; j++) {

				if (lru[j] == null) { // 값을 넣을 곳이 비어있을 경우

					increase(lru);

					lru[j] = new Algorism(lru, num[i]);

					count++;

					replacement++;

					j = lru.length;

					break;

				}

				else if (lru[j].getNum() == num[i]) { // 생성된 난수와 이미 같은 값이 있을경우 즉, Hit 했을경우

					increase(lru);

					System.out.println("Hit!");

					lru[j].setCount(1); // 카운트를 다시 1로 만들어 줌

					hit++;

					j = lru.length;

					break;

				}

				else if (count == lru.length && same(lru, num[i]) == 0) { // 칸이 모두 차있고 같은 값이 없을경우

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

	public static double lfuAlgorism(Algorism[] lfu, int num[]) {// LFU 알고리즘
		//long time1=System.nanoTime();

		int count = 0;// 배열에 null 값이 있는지를 확인하기 위한 변수

		int hit = 0;//전체 적중 개수

		int replacement = 0;//전체 미스 개수

		for (int i = 0; i < num.length; i++) {

			System.out.println("숫자: " + num[i]);

			for (int j = 0; j < lfu.length; j++) {

				if (lfu[j] == null) { // 값을 넣을 곳이 비어있을 경우,miss

					increasePoint(lfu);//모든 적재 순서 count

					lfu[j] = new Algorism(lfu, num[i]);//lfu 새로운 객체 생성(적재)

					count++;

					replacement++;

					j = lfu.length;

					break;

				}

				else if (lfu[j].getNum() == num[i]) { // 생성된 난수와 이미 같은 값이 있을경우 즉, Hit 했을경우

					increasePoint(lfu);

					System.out.println("Hit!");

					lfu[j].setFPoint(0); // 적중했으므로 적재 순서 0으로 초기화->FIFO 사용 시 빈도 카운트가 낮아야 교체되지 않음

					lfu[j].countIncrease(); // 사용 빈도 증가

					hit++;//적중 횟수 증가
					
					j = lfu.length;

					break;

				}

				else if (count == lfu.length && same(lfu, num[i]) == 0) { // 칸이 모두 차있고 같은 값이 없을경우-->FIFO 사용

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
//		//System.out.println("알고리즘 수행 시간 : "+(time2-time1)/1000.0+"ns");
//		AlgTime[1]=(long) ((time2-time1)/1000.0);
//		
		return Math.round(((double)hit/num.length)*1000.0)/1000.0;

	}
	
	
	 static double fifoAlgorism(int[] num,int size) {// FIFO 알고리즘
	//		long time1=System.nanoTime();

	      int count=0;//hit count
	      
	      Queue<Integer> queue =new LinkedList<>();
	      for(int i=0; i<num.length;i++) {
	    	 System.out.println("숫자 : "+num[i]);
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
			//System.out.println("알고리즘 수행 시간 : "+(time2-time1)/1000.0+"ns");
	//		AlgTime[2]=(long) ((time2-time1)/1000.0);
			
			return Math.round(((double)count/num.length)*1000)/1000.0;
	      }	


	public static double randomAlgorism(Algorism[] random, int num[]) { // RANDOM 알고리즘
		//long time1=System.nanoTime();

		int hit = 0;

		int replacement = 0;

		for (int i = 0; i < num.length; i++) {

			System.out.println("숫자: " + num[i]);

			int r = (int) (Math.random() * random.length);

			for (int j = 0; j < random.length; j++) {

				if (same(random, num[i]) > 0) { // Hit 했을경우

					increase(random);

					System.out.println("Hit!");

					hit++;

					j = random.length;

					break;

				}

				else { // Hit 하지 않은 경우

					//배열의 랜덤한 r번째에 값을 넣음

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
		//System.out.println("알고리즘 수행 시간 : "+(time2-time1)/1000.0+"ns");
		//AlgTime[3]=(long) ((time2-time1)/1000.0);
		
		return Math.round(((double)hit/num.length)*1000)/1000.0;

	}

	public static void increase(Algorism[] a) { // 카운트 증가를 위한 메소드

		for (int i = 0; i < a.length; i++) {

			if (a[i] != null)

				a[i].countIncrease();

		}

	}

	public static void increasePoint(Algorism[] a) { // 포인트 증가를 위한 메소드

		for (int i = 0; i < a.length; i++) {

			if (a[i] != null)

				a[i].pointIncrease();

		}

	}

	public static void print(Algorism[] a) { // 배열에 넣어져 있는 숫자 출력을 위한 메소드

		for (int i = 0; i < a.length; i++) {

			if (a[i] != null)

				System.out.print(a[i].getNum() + " ");

			else

				System.out.print("x ");

		}

	}

	public static void count(Algorism[] a) { // 현재 카운트를 확인하기 위한 메소드

		System.out.print("카운트 : ");

		for (int i = 0; i < a.length; i++) {

			if (a[i] != null)

				System.out.print(a[i].getCount() + " ");

			if (a[i] == null)

				System.out.print(0 + " ");

		}
		System.out.println();
	}

	public static int same(Algorism[] a, int num) { // 배열안에 현재 받은 숫자와 일치하는 숫자가 있는지 검사하는 메소드

		int sameCount = 0;

		for (int i = 0; i < a.length; i++) {

			if (a[i] == null)

				continue;

			else if (a[i].getNum() == num)

				sameCount++;

		}

		return sameCount;

	}

	public static int compare(Algorism[] a) { // 교체를 위해 카운트가 제일 높은 곳 을 찾아주는 메소드

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

	public static int compare2(Algorism[] a) { // LFU알고리즘에서 사용빈도가 낮은것 중에서 적재가 가장 오래된 곳을 찾아주는 메소드

		int compareNum = a[0].getCount();

		int compare = 0;//교체 시 몇 번째 슬롯에서 교체가 일어나는지 index 확인

		int comparePoint = a[0].getFPoint();

		for (int i = 0; i < a.length; i++) {
			
			if (compareNum > a[i].getCount()) {
		
				compareNum = a[i].getCount();
				comparePoint = a[i].getFPoint();
				compare = i;
			
			} else if (compareNum == a[i].getCount() && comparePoint < a[i].getFPoint()) {
				//빈도수가 같을 때 FIFO 방식으로 교체(fpoint가 높을수록 오래 있던 슬롯)
				
				comparePoint = a[i].getFPoint();
				compare = i;
			}
		}

		return compare;

	}

	public static void hitRatio(double lru, double lfu, double fifo, double random) { // 4개의 알고리 즘의 Hit Ratio 를 비교하여 높은것 출력
		System.out.print("적중률이 가장 높은 것은");
		if (lru >= lfu && lru >= fifo && lru >= random)
			System.out.print(" LRU");
		if (lfu >= lru && lfu >= fifo && lfu >= random)
			System.out.print(" LFU");
		if (fifo >= lru && fifo >= lfu && fifo >= random)
			System.out.print(" FIFO");
		if (random >= lru && random >= lfu && random >= fifo)
			System.out.print(" RANDOM");
		System.out.println(" 입니다.");
	}
	
	public static String Pattern1(int[] num, int length1,int length2) {//규칙적이지 않은 패턴
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
			
			
			if(length1!=0&&num[i]==array1[0]) {//패턴 1이 짝수번째 일치할 때 실행
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
			}else if(length2!=0&&num[i]==array2[0]) {//패턴 2가 홀수번째 일치할 때 실행
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
		
		return pattern;//pattern 반환
	}
	
	 public static void Pattern2(int data[]) {//규칙적인 패턴
	      System.out.print("반복 개수 : ");
	      Random r= new Random();
	      int re=input.nextInt();
	      System.out.print("데이터 몇개 반복?");
	      int rearr = input.nextInt();
	      input.nextLine();
	      System.out.println("데이터 패턴 입력 : ");
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
