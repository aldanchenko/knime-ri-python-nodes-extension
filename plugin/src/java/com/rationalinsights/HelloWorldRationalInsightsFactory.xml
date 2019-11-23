<?xml version="1.0" encoding="UTF-8"?>
<knimeNode icon="./default.png" type="Manipulator"
	xmlns="http://knime.org/node/v2.8" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://knime.org/node/v2.10 http://knime.org/node/v2.10.xsd">
	<name>HelloWorldRationalInsights</name>

	<shortDescription>
		[Enter short description here.]
	</shortDescription>

	<fullDescription>
		<intro>[Enter long description here.]</intro>


		<option name="Counter">description of first option</option>
		<!-- possibly more options that can also be grouped by tabs -->
		<!-- <tab name="Standard Options"> <option name="short name of first option 
			(like in the dialog)">description of first option</option> <option name="short 
			name of second option (like in the dialog)">description of second option</option> 
			</tab> <tab name="Advanced Options"> <option name="short name of first advanced 
			option (like in the dialog)">description of first advanced option</option> 
			</tab> -->
		<tab name="Python options">
			<option name="Use python version">
				Allows to choose the python version that
				should be used for
				executing the kernel. Available
				installations can be checked
				under Preferences → KNIME →
				Python.
			</option>
			<option name="Missing values (Int, Long)">
				By default, Int and Long columns containing
				missing values are
				converted to doubles in Python, because
				doubles
				are the
				only numeric type
				containing a built-in missing
				value
				representation (NaN). Converting longs to doubles,
				however,
				introduces imprecision. The
				conversion may be
				avoided
				by replacing
				missing values with so-called sentinel values.
				To convert missing
				values to
				sentinel values before script
				execution, check the first
				option, to convert sentinel values
				back to missing values after
				script execution, check the
				second option. To select a sentinel
				value, click on
				MIN_VAL
				(the smallest possible value for the
				column's
				data type),
				MAX_VAL (the largest possible
				value for the column's data
				type), or a manually enter an integer in the text field.
			</option>
			<option name="Rows per chunk">
				Large tables are broken into chunks when
				they are transferred between
				Python and Java. This option
				controls how many rows are
				included in
				each chunk. The default
				value is intended for tables containing few
				columns having
				numeric types. If larger data types like
				images or text
				are
				transferred, a single chunk may exceed the
				maximum allowed
				buffer size. In this case lowering the value of this
				parameter helps getting reasonably sized chunks.
			</option>
		</tab>
	</fullDescription>

	<ports>
		<inPort index="0" name="In-Port name">Description of first input port...
		</inPort>
		<!-- possibly more input ports here -->
		<outPort index="0" name="Out-Port name">Description of first output port...
		</outPort>
		<!-- possibly more output ports here -->
	</ports>
	<views>
		<view index="0" name="name of first view">Description of first view...</view>
		<!--view index="1" name="name of second view">Description of second view...</view -->
	</views>
</knimeNode>
