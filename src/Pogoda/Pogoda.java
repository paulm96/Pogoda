package Pogoda;

import java.awt.EventQueue;
import java.awt.Frame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.*;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.ChangedCharSetException;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;

public class Pogoda {

	private JFrame frame;
	private JTextField TextFieldCity;
	private JScrollPane pane = null;
	public String actualWeather = null;
	public String weatherForecast = null;
	private JScrollPane ScrollPaneCurrent = null;
	private JTable JTableDB = null;
	private JTable JTableCurrent = null;
	private JScrollPane ScrollPaneCyclic = null;
	private JTable JTableCyclic = null;
	private CyclicWeatherChecker cyclicWeatherChecker = null;
	private JButton DBDisplayButton = null;
	private JButton DBDeleteButton = null;
	private JButton DBDeleteSpecificButton = null;
	private JPanel panel_1 = null;
	private JTextField textField;
	private JTextField currentStatus;
	private JScrollPane scrollPaneForecast = null;
	private JTable jTableForecast = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pogoda window = new Pogoda();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Pogoda() {
		initialize();
	}
	
	//to get current weather
	private StringBuilder weatherChecker(String city) {
		actualWeather = "http://api.openweathermap.org/data/2.5/weather?q="+ city + ",pl&APPID=742070efe45d2e97d12202fbc91af4f7";
		StringBuilder output = null;
		URL url=null;
		

        try {
        	
            String a=actualWeather;
            url = new URL(a);
            URLConnection conn = url.openConnection();
            
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
            output = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                    System.out.println(inputLine);
                    output.append(inputLine);
            }
            br.close();

            System.out.println("Done");

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return output;
	}
	
	private StringBuilder forecastChecker(String city) {
		actualWeather = "http://api.openweathermap.org/data/2.5/forecast?q="+ city + ",pl&APPID=742070efe45d2e97d12202fbc91af4f7";
		StringBuilder output = null;
		URL url=null;
		

        try {
        	
            String a=actualWeather;
            url = new URL(a);
            URLConnection conn = url.openConnection();
            
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
            output = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                    System.out.println(inputLine);
                    output.append(inputLine);
            }
            br.close();

            System.out.println("Done");

        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return output;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 843, 657);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 371, 376);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		DBDisplayButton = new JButton("Wy\u015Bwietl z bazy");
		DBDisplayButton.setBounds(10, 11, 157, 23);
		panel.add(DBDisplayButton);
		
		DBDeleteButton = new JButton("Usu\u0144 baz\u0119");
		DBDeleteButton.setBounds(238, 11, 118, 23);
		panel.add(DBDeleteButton);
		
		DBDeleteSpecificButton = new JButton("Usu\u0144 wybrane");
		DBDeleteSpecificButton.setBounds(10, 39, 157, 23);
		panel.add(DBDeleteSpecificButton);
		

		DefaultTableModel models = new DefaultTableModel();
		
		
		JTableDB = new JTable(models);
		models.addColumn("City");
		models.addColumn("Temperature");
		models.addColumn("Pressure");
		models.addColumn("Time");
		JTableDB.getColumnModel().getColumn(3).setPreferredWidth(130);
		pane = new JScrollPane(JTableDB);
		pane.setAutoscrolls(true);
		pane.setBounds(10,85,346,274);
		panel.add(pane);
		
		panel_1 = new JPanel();
		panel_1.setBounds(376, 0, 455, 376);
		
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 381, 371, 247);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_2.setLayout(null);
		frame.getContentPane().add(panel_2);
		
		TextFieldCity = new JTextField();
		TextFieldCity.setBounds(140, 26, 86, 20);
		panel_2.add(TextFieldCity);
		TextFieldCity.setColumns(10);
		
		JLabel lblMiasto = new JLabel("Miasto:");
		lblMiasto.setBounds(167, 11, 46, 14);
		panel_2.add(lblMiasto);
		
		JButton btnSprawd = new JButton("Sprawdz pogod\u0119");
		btnSprawd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuilder output = weatherChecker(TextFieldCity.getText());

				if(output == null) {
					currentStatus.setText("Brak dostepu do Internetu lub bledna nazwa miasta");
					return;
				}
				currentStatus.setText("");
		        JSONObject weather = null;
		        try {
					weather = new JSONObject(output.toString());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        int temperature = 0;
		        JSONObject mainObject = null;
				try {
					mainObject = weather.getJSONObject("main");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					temperature = mainObject.getInt("temp");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				int pressure = 0;
				try {
					pressure = mainObject.getInt("pressure");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				DefaultTableModel model = new DefaultTableModel();

				JTableCurrent = new JTable(model);
			
				model.addColumn("City");
				model.addColumn("Temperature");
				model.addColumn("Pressure");
				model.addRow(new Object[] {TextFieldCity.getText(), Integer.toString(temperature-273), Integer.toString(pressure)});
				
				ScrollPaneCurrent = new JScrollPane(JTableCurrent);
				ScrollPaneCurrent.setBounds(10, 110, 350, 45);
				panel_2.add(ScrollPaneCurrent);
				
				
		    }
		});
		
		btnSprawd.setBounds(110, 46, 145, 23);
		panel_2.add(btnSprawd);
		
		currentStatus = new JTextField();
		currentStatus.setEditable(false);
		currentStatus.setColumns(10);
		currentStatus.setBounds(27, 216, 307, 20);
		panel_2.add(currentStatus);
		
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(376, 381, 455, 242);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_3.setLayout(null);
		frame.getContentPane().add(panel_3);
		
		JLabel lblObecnaPogoda = new JLabel("Obecna pogoda (aktualizowane automatycznie):");
		lblObecnaPogoda.setBounds(110, 22, 280, 14);
		panel_3.add(lblObecnaPogoda);
		
		DefaultTableModel model = new DefaultTableModel();

		JTableCyclic = new JTable(model);
		
		model.addColumn("City");
		model.addColumn("Temperature");
		model.addColumn("Pressure");
		model.addColumn("Time");
		JTableCyclic.getColumnModel().getColumn(3).setPreferredWidth(100);
		ScrollPaneCyclic = new JScrollPane(JTableCyclic);
		ScrollPaneCyclic.setBounds(10, 47, 435, 48);		
		panel_3.add(ScrollPaneCyclic);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(141, 211, 171, 20);
		panel_3.add(textField);
		textField.setColumns(10);
		
		DefaultTableModel model2 = new DefaultTableModel();
		jTableForecast = new JTable(model2);
		model2.addColumn("Temperature(morning)");
		model2.addColumn("Temperature(evening)");
		scrollPaneForecast = new JScrollPane(jTableForecast);
		scrollPaneForecast.setBounds(10, 125, 435, 48);
		panel_3.add(scrollPaneForecast);
		
		JLabel lblPrognozaJutrzejszaaktualizowane = new JLabel("Prognoza jutrzejsza (aktualizowane automatycznie):");
		lblPrognozaJutrzejszaaktualizowane.setBounds(99, 106, 310, 14);
		panel_3.add(lblPrognozaJutrzejszaaktualizowane);

		cyclicWeatherChecker = new CyclicWeatherChecker();
	}
	
	public class CyclicWeatherChecker implements ActionListener {
		WeatherDB database = new WeatherDB();
		private List<WeatherDB> weatherDB;
		public int time;
		
		public CyclicWeatherChecker() {		
			createChart();
			
			Timer timer = new Timer(1000,this);
			time = 9;
			timer.start();
			
			DBDisplayButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					refreshView();
				}
			});
			
			DBDeleteButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					database.deleteAllRows();
					refreshView();
					createChart();
				}
			});
			
			DBDeleteSpecificButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					int row = JTableDB.getSelectedRow();				
					String value = (String) JTableDB.getModel().getValueAt(row, 3);
					database.deleteRow(value);
					refreshView();
					createChart();
				}
			});
			
			}
		
		public void refreshView() {
			weatherDB = database.selectWeather(); //stad usunac to, jak bedzie wykres to bedzie to robione co jedno dodanie rekordu
			DefaultTableModel model = (DefaultTableModel)JTableDB.getModel();
			if (model.getRowCount() > 0) {
			    for (int i = model.getRowCount() - 1; i > -1; i--) {
			        model.removeRow(i);
			    }
			}
								
			for(WeatherDB w : weatherDB) {
				model.addRow(new Object[] {w.getCity(), w.getTemperature(), w.getPressure(), w.getDate()});
			}
			
			JTableDB.repaint();
			JTableDB.invalidate();
		}
		
		public void createChart() {
			//Making chart
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();				
			weatherDB = database.selectWeather();
			for(WeatherDB db : weatherDB) {					
				dataset.addValue(Integer.parseInt(db.getTemperature()), "Temperature", db.getDate());
			}
			JFreeChart lineChart = ChartFactory.createLineChart("Temperatura dla Wroc³awia", "Date", "Temperature", dataset, PlotOrientation.VERTICAL, true, true,false);

			ChartPanel chartPanel = new ChartPanel(lineChart);
			
			panel_1.removeAll();
			panel_1.add(chartPanel);
			panel_1.validate();
			panel_1.setVisible(true);
			chartPanel.setVisible(true);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			time++;
			if(time%10 == 0) {
			
				StringBuilder output = weatherChecker("Wroclaw");
				if(output == null) {
					textField.setText("Brak dostepu do Internetu");
					return;
				}
				textField.setText("");
				JSONObject weather = null;
		        try {
					weather = new JSONObject(output.toString());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		        int temperature = 0;
		        JSONObject mainObject = null;
				try {
					mainObject = weather.getJSONObject("main");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					temperature = mainObject.getInt("temp");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				int pressure = 0;
				try {
					pressure = mainObject.getInt("pressure");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//to get forecast
				StringBuilder foutput = forecastChecker("Wroclaw");
				JSONObject fweather = null;
		        try {
					fweather = new JSONObject(foutput.toString());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		       JSONArray flist = null;
		       try {
				flist = fweather.getJSONArray("list");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		       
		       int tomorrow_morning_temperature=0;
		       int tomorrow_evening_temperature=0;
		       for(int i = 0; i < flist.length(); i++) {
		    	   try {
					JSONObject item = flist.getJSONObject(i);
					String date = item.getString("dt_txt");
					
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, 1);
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
					if(date.contains(timeStamp+" 09:00:00")) {
						//tutaj pogoda drugiego dnia rano
						JSONObject main = item.getJSONObject("main");
						tomorrow_morning_temperature = main.getInt("temp")-273;
					}
					if(date.contains(timeStamp+" 18:00:00")) {
						//tutaj pogoda drugiego dnia wieczorem
						JSONObject main = item.getJSONObject("main");
						tomorrow_evening_temperature = main.getInt("temp")-273;
					}
					
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	   
		       }
				
				//To get current time
				String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
				
				//Inserting "current" data to database
				database.insertWeather("Wroclaw", Integer.toString(temperature-273), Integer.toString(pressure), timeStamp);
				
				
				//Refreshing jTable to display forecast
				DefaultTableModel model2 = (DefaultTableModel)jTableForecast.getModel();
				if(model2.getRowCount() != 0) {
					model2.setValueAt(Integer.toString(tomorrow_morning_temperature), 0, 0);
					model2.setValueAt(Integer.toString(tomorrow_evening_temperature), 0, 1);
				} else {
					model2.addRow(new Object[] {Integer.toString(tomorrow_morning_temperature), Integer.toString(tomorrow_evening_temperature)});
				}
				jTableForecast.repaint();
				jTableForecast.invalidate();
				
				
				//Refreshing jTable to display current weather 
				DefaultTableModel model = (DefaultTableModel)JTableCyclic.getModel();
				
				if(model.getRowCount() != 0) {
					model.setValueAt(Integer.toString(temperature-273), 0, 1);
					model.setValueAt(Integer.toString(pressure), 0, 2);
					model.setValueAt(timeStamp, 0, 3);
				} else {
					model.addRow(new Object[] {"Wroclaw", Integer.toString(temperature-273), Integer.toString(pressure), timeStamp});
				}
				JTableCyclic.repaint();
				JTableCyclic.invalidate();
				
				createChart();
				
				
				
			}
			

		}
		
	}
}
