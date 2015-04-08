package m.serial.drivers;

import java.util.ArrayList;
import java.util.List;

import jssc.*;

public class DS1820 {
	private static final int bauadRate = SerialPort.BAUDRATE_115200;
	private static final int dataBits = SerialPort.DATABITS_8;
	private static final int stopBits = SerialPort.STOPBITS_1;
	private static final int parity = SerialPort.PARITY_NONE;
	private static final double TEMP_ERROR = -999.99;
	private CRoms  sensor_list;
    private SerialPort hCom;
    private boolean LastDeviceFlag;
    private int ReadTries;
    private char unknown[];
    private char SerialNum[];
	private int read_time;
	
	public DS1820()
	{
		unknown = new char[8];
		SerialNum = new char [8];
	    hCom=null;
		setReadTime(1000);
		setReadTries(3);
		sensor_list= new CRoms();
	}
	
	public void setReadTries(int tries){
	    ReadTries=tries;
	}
	
	public void setReadTime(int time_ms){
	    read_time = time_ms;
	}
	
	public void setSerialNum(char serial[]){
	    for (int i = 0; i < 8; i++){
				SerialNum[i] = serial[i];
	    }
	}
	
	public char[] getSerialNum(){
		char serial[] = new char[8];
	    for (int i = 0; i < 8; i++){
	        serial[i] = SerialNum[i];
	    }
		return serial;
	}

	public boolean InitCOM(String serial_port)
	{
		if(hCom!=null){
			if(hCom.isOpened()){
				try {
					hCom.closePort();
				} catch (SerialPortException e1) {
					e1.printStackTrace();
				}
			}
		}
	    sensor_list.clearRomsList();
	    
		hCom = new SerialPort(serial_port);
		try {
		
			hCom.openPort();// Open serial port
			hCom.setParams(bauadRate, dataBits, stopBits, parity);
			//to do Time Out exception in read function
		} catch (SerialPortException e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	public void termitCom(){
	    if(hCom.isOpened())
			try {
				hCom.closePort();
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
	    sensor_list.clearRomsList();
	}
	
	//SEARCH FUNCTIONS
	public int InitBus() // inicjalizacja szyny
	{
		char TempSN[];
		boolean  result;
	    result = FindFirst();
		while(result)
		{
			TempSN = getSerialNum();
	        if( (TempSN[0] == 0x28) ||  (TempSN[0] == 0x10))
			{
	        	char temp[] = new char[8];
	        	temp = getSerialNum();
	        	sensor_list.roms.add(temp);
//            	temp = sensor_list.roms.get(i);
//            	buffor.add(temp);
//Powiekszanie kolekcji
//				List<Character[]> buffor = new ArrayList<Character[]>();
//	            for(int i=0; i<sensor_list.max; i++){
//	            	Character temp[] = new Character[8];
//	            	temp = sensor_list.roms.get(i);
//	            	buffor.add(temp);
//	            }
//				sensor_list.max++;
//	            sensor_list.clearRomsList(); //tu moze byc bład trzeba sprawdzic z dwoma czujnikami i uzyc realloc ewentualnie
//	            if(!(sensor_list.roms = new unsigned char [sensor_list.max*8]))
//				{
//					if( sensor_list.roms )
//					{
//						delete [] sensor_list.roms;
//						sensor_list.roms = NULL;
//			        }
//					return -1;
//				}
//
//				for(int i=0; i<(sensor_list.max-1)*8; i++) sensor_list.roms[i]=buffor[i];
//				getSerialNum(&sensor_list.roms[(sensor_list.max-1)*8]);
//				delete [] buffor;
			}
	        result = FindNext();
		}
		return sensor_list.roms.size();
	}

	boolean FindFirst()
	{
	 	LastDeviceFlag = false;
	    for(int x = 0; x < 8; x++ ) unknown[x] = 0xFF;

		return FindNext();
	}

	boolean FindNext()
	{
		int done=-1;
	    char tmp_serial_num[] = new char[8];
	    int i;

	    byte bits;

		if (LastDeviceFlag)
		{
			LastDeviceFlag = false;
			return false;
		}

		if(!SendReset()) return false;
	    if(!SendSearch()) return false;

		for( i = 0; i < 64; i++ )//zczytywanie numerów seryjnego tablica unknow pomaga nam gdy nie mozemy zczytac danego bitu, tmp_serial_num przechowuje numer seryjny, wpisywane tam jest bit po bicie adres,
		{
			bits = TouchBits(2, (byte)0xFF);
			switch(bits & 0x03)
			{
			case 0x00 :  //czujniki maja rózne bity -> jezeli 1 to wpisz 1 jezeli 0 to 0

				if(((unknown[i/8] >> i%8) & 0x01)!=0)
	            {
					TouchBits(1, (byte)0xFF);
					tmp_serial_num[i/8] |= (0x01 << i%8);//dopisywanie jeden na odpowiednim bicie
	            }
				else
				{
					TouchBits(1, (byte)0x00 );
					tmp_serial_num[i/8] &= ~(0x01 << i%8);//dopisywanie zera na odpowiednim bicie
	            }
	            break;

			case 0x01 :  //wszystkie maja 1 na tej pozycji
	            TouchBits(1, (byte)0xFF );
	            tmp_serial_num[i/8] |= (0x01 << i%8);
	            unknown[i/8] &= ~(0x01 << i%8);// zaznaczanie ze nie jest nieznamy
	            break;

			case 0x02 :  //wszystkie maja 0 na tej pozycji
				TouchBits(1, (byte)0x00 );
	            tmp_serial_num[i/8] &= ~(0x01 << i%8);
	            unknown[i/8] &= ~(0x01 << i%8);
	            break;

			case 0x03 :  //nic nie podlaczone
				return false;
			default :
				break;
			}
		}

		for( i = 63; i >= 0; i-- )
		{
			//mod
			if(((unknown[(i)/8] >> (i)%8)& 0x01)==0)
			{
				unknown[(i)/8] |= (0x01 << (i)%8);
			}
			else
			{
	            unknown[i/8] &= ~(0x01 << i%8);
				done = 0;
				break;
			}
		}

	    if (done == -1) LastDeviceFlag = true;

	    for (i = 0; i < 8; i++){
	        SerialNum[i] = tmp_serial_num[i];
	    }
	    return true;
	}
	
	
	//SEND FUNCTIONS
	byte TouchBits(int nbits,byte outch)
	{
		byte mask;
	    byte ans;
		byte message;

	    mask = 0x01;
	    ans = 0;

		try {
			hCom.purgePort(SerialPort.PURGE_RXCLEAR);        //czyść buffor
			for (int i = 0; i < nbits; ++i)
			{
				message = ((outch & mask)!=0) ? (byte)0xff : (byte)0x00;
				//wyslij bajt a sensor odczyta to jako bit - predkosc 115200 bitów / s i 8 bitów wysyłanych daje impuls ponad 60us, z odczytem podobnie
				if (!hCom.writeByte(message)) throw new SerialPortException("port0","methd","error");
				message = hCom.readBytes(1, 1500)[0];
		        ans |= ((message & 0x01)!=0) ? mask : 0;
				mask <<= 1;
		    }
		} catch (SerialPortException | SerialPortTimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	    return ans;
	}

	boolean SendBlock(byte buf[] )
	{
		for(int i = 0; i < buf.length; i++)
		{
			buf[i] = TouchBits(8,buf[i]);
		}

		return true;
	}

	boolean SendReset()
	{
	    byte message;
		boolean stat = false;

		try {
			hCom.setParams(9600, 8, 1, 0);
			message = (byte) 0xf0;//wolniejsza transmisja 4 bity na 1, 4 bity na 0;

			hCom.purgePort(SerialPort.PURGE_RXCLEAR);
	
			hCom.writeByte(message);
			message = hCom.readBytes(1,1500)[0];
	
			stat = (message!=0xF0) ? true : false;
	
			hCom.setParams(bauadRate, dataBits, stopBits, parity);
		} catch (SerialPortException | SerialPortTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  stat;
	}

	boolean SendMatchROM(char[] TempSN)
	{
		int x;

	    if(!SendReset())
		{
			return false;
		}

		if(TouchBits(8,(byte)0x55 ) < 0 )
		{
			return false;
		}

		for( x = 0; x < 8; x++ )
		{
			TouchBits(8,(byte)TempSN[x]);
		}

		return true;
	}

	boolean SendReadTemperature()
	{
		if(TouchBits(8,(byte) 0x44 ) < 0 )
		{
			return false;
		}
		try {
			Thread.sleep(read_time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	boolean SendReadScratchpad(char [] spad)
	{
		int   x;

		TouchBits(8,(byte)0xBE);

		for( x = 0; x < 9; x++ )
		{
			spad[x] = (char) TouchBits(8, (byte)0xFF );
		}

		if(!SendReset())
		{
			return false;
		}

	  return true;
	}

	boolean SendSearch(){
	    TouchBits(8,(byte)0xF0);
	    return true;
	}
	
	double CalcTemperature(char [] scratchpad, char sensor_family)
	{
	    double hi_precision;
	    double temp_c = -999.99;
	    if( (sensor_family == 0x28))
	    {
	        int temp = (scratchpad[1] << 8) | scratchpad[0];
	        temp_c = temp / 16.0;
	    }
	    else if( sensor_family == 0x10)
	    {
	        if( scratchpad[1] == 0 )
	        {
	            temp_c = (int) scratchpad[0] >> 1;
	        }
	        else
	        {
	            temp_c = -1 * (int) (0x100-scratchpad[0]) >> 1;
	        }
	        temp_c -= 0.25;
	        hi_precision = (int) scratchpad[7] - (int) scratchpad[6];
	        hi_precision = hi_precision / (int) scratchpad[7];
	        temp_c = temp_c + hi_precision;
	    }
	    //SendReset();
	    return temp_c;
	}

	public double GetTemperature(int sensor)
	{
		char TempSN[] = new char[8];
	    char scratchpad[] = new char [9];
	    int prob;

		if( sensor < sensor_list.roms.size() && sensor >=0)
		{
			setSerialNum(sensor_list.roms.get(sensor));
		}
		else return TEMP_ERROR;

		TempSN = getSerialNum();

		for( prob = 0; prob < ReadTries; prob++ )//petla odczytu
		{
			if (!SendMatchROM(TempSN)) continue;
	        if (!SendReadTemperature()) continue;
			if (!SendMatchROM(TempSN)) continue;
			if (SendReadScratchpad(scratchpad)) return CalcTemperature(scratchpad, TempSN[0]);
		}
		return TEMP_ERROR;
	}

	public double GetTemperature(String rom_code)
	{
	    for(int i=0;i<sensor_list.roms.size();i++)
	        if(!GetRomCode(i).equals(rom_code))
	            return GetTemperature(i);
	    return TEMP_ERROR;
	}

	String GetRomCode(int sensor)
	{
	    if(sensor<0 || sensor>sensor_list.roms.size()-1 || sensor_list.roms.size()<1){
	        return "";
	    }
	    else
	    {
	        char[] rom=sensor_list.roms.get(sensor);

	        String code = new String("");
	        for(int i=0;i<8;i++){
	            code = code + String.format("%02X",rom[i]);
	        }
	        return code;
	    }
	}
	
}
class CRoms
{
    public List<char[]> roms;

    CRoms(){
        roms = new ArrayList<char[]>();
    }
    
    public void clearRomsList(){
    	roms.clear();
    }
};