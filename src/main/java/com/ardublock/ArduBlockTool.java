package com.ardublock;

import com.ardublock.core.Context;
import com.ardublock.ui.ArduBlockToolFrame;
import com.ardublock.ui.listener.OpenblocksFrameListener;
import processing.app.Editor;
import processing.app.tools.Tool;

import java.io.*;

public class ArduBlockTool implements Tool, OpenblocksFrameListener
{
	static Editor editor;
	static ArduBlockToolFrame openblocksFrame;
	
	public void init(Editor editor) {
		if (ArduBlockTool.editor == null )
		{
			ArduBlockTool.editor = editor;
			ArduBlockTool.openblocksFrame = new ArduBlockToolFrame();
			ArduBlockTool.openblocksFrame.addListener(this);
			Context context = Context.getContext();
			String arduinoVersion = this.getArduinoVersion();
			context.setInArduino(true);
			context.setArduinoVersionString(arduinoVersion);
			context.setEditor(editor);
			System.out.println("Arduino Version: " + arduinoVersion);
		}
	}

	public void run() {
		try {
			ArduBlockTool.editor.toFront();
			ArduBlockTool.openblocksFrame.setVisible(true);
			ArduBlockTool.openblocksFrame.toFront();
		} catch (Exception e) {

		}
	}

	public String getMenuTitle() {
		return Context.APP_NAME;
	}

	public void didSave() {
		
	}
	
	public void didLoad() {
		
	}
	
	public void didSaveAs()
	{
		
	}
	
	public void didNew()
	{
		
	}
	
	public void didGenerate(String source) {
		ArduBlockTool.editor.setText(source);
		ArduBlockTool.editor.handleExport(false);
	}
	
	private String getArduinoVersion()
	{
		Context context = Context.getContext();
		File versionFile = context.getArduinoFile("lib/version.txt");
		if (versionFile.exists())
		{
			try
			{
				InputStream is = new FileInputStream(versionFile);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String line = reader.readLine();
				reader.close();
				if (line == null)
				{
					return Context.ARDUINO_VERSION_UNKNOWN;
				}
				line = line.trim();
				if (line.length() == 0)
				{
					return Context.ARDUINO_VERSION_UNKNOWN;
				}
				return line;
				
			}
			catch (FileNotFoundException e)
			{
				return Context.ARDUINO_VERSION_UNKNOWN;
			}
			catch (UnsupportedEncodingException e)
			{
				return Context.ARDUINO_VERSION_UNKNOWN;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return Context.ARDUINO_VERSION_UNKNOWN;
			}
		}
		else
		{
			return Context.ARDUINO_VERSION_UNKNOWN;
		}
		
	}
}
