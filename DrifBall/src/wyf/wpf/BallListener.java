package wyf.wpf;				//��������� 
import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;

import android.hardware.SensorManager;
//���������
//���������
/*
 * ����̳���SensorListener����Ҫ�����Ǹ�Ӧ����������̬����
 * ͨ��onSensorChanged������ȡ����,������RoatatUtil�еľ�̬
 * ������ȷ��С��Ӧ���ƶ��ķ���
 */
public class BallListener implements SensorEventListener{
	DriftBall father;		//DriftBall����
	int timeSpan = 500;		//500MS���һ��
	long startTime;			//��¼��ʼ��ʱ��
	
	public BallListener(DriftBall father){		//����������ʼ����Ա����
		this.father = father;
		startTime = System.currentTimeMillis();	//��¼ϵͳʱ����Ϊ��ǰʱ��
	}
	public void analysisData(float[] values) {		//�Զ�ȡ����̬���ݽ��з���������RoateUtil�ľ�̬��������������
		double[] valuesTemp=new double[]{values[0],-values[1],values[2]};	//��y���������
		father.gv.direction = RotateUtil.getDirectionCase(valuesTemp);	//����RotateUtil�ľ�̬���������С����
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		long now = System.currentTimeMillis();		//��ȡϵͳ��ǰʱ��
		if(now - startTime >= timeSpan){			//�ж��Ƿ��߹�ָ����ʱ����
			if(arg0.type == SensorManager.SENSOR_ORIENTATION){
				analysisData(arg0.values);		//����analysisData���������ݽ��з���
			}	
			startTime = now;				//���¼�ʱ
		}
	}	
}