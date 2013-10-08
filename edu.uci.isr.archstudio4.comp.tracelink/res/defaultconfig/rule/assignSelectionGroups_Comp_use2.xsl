<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" indent="yes"/>

<xsl:strip-space elements="*" />

<!-- This template goes through each top level object element and groups the 
     selection objects together based on the component/connector.  Thus, artifacts
		 that are selected after a component/connector are grouped with the component/connector 
		 
		 Run this after removeSelectionBasedOnTime-->
	
 <xsl:template match="/">
  <java version="1.6.0_05" class="java.beans.XMLDecoder">test10
      <xsl:for-each select="//void[@property='timeStamp']/object">
			 
			  <xsl:if test="position() &gt; last()">
			      <xsl:call-template name = "done">
						</xsl:call-template>
			  </xsl:if>
				<!-- <xsl:if test="position() &gt; 1">  -->
 			        <xsl:call-template name ="copyNodes">
								 <xsl:with-param name="curLocation" select="position()"/>
						  </xsl:call-template>
					 
				 <!-- </xsl:if>  -->
      </xsl:for-each>
  </java>
 </xsl:template>



 <!-- This is a valid selection -->
 <xsl:template name="copyNodes">
  <xsl:param name="curLocation"/>
  <xsl:apply-templates select="ancestor::object" mode="gen">
	  <!-- <xsl:with-param name="pPath" select="ancestor::void/parent::object/descendant::*" />  -->
		<!-- <xsl:with-param name="groupVal" select="number(./ancestor::object/preceding-sibling::object[1]/void[@property='groupNum']/int)" />  -->
		<!-- <xsl:with-param name="groupValue" select="1"/>  -->
		<xsl:with-param name="curLocation" select="$curLocation"/>
  </xsl:apply-templates>
 </xsl:template>
 
 <!-- 
 <xsl:template name="incrementGroup">
  <xsl:param name="curLocation"/> 
  <xsl:apply-templates select="ancestor::object" mode="gen">
	  <xsl:with-param name="pPath" select="ancestor::void/parent::object/descendant::*" />
		<xsl:with-param name="groupVal" select="number(./ancestor::object/preceding-sibling::object[1]/void[@property='groupNum']/int) + number(1)" />
		<xsl:with-param name="curLocation" select="$curLocation"/>
  </xsl:apply-templates>
 </xsl:template>
 -->
 
 <xsl:template match="object" mode="gen">  
  <!-- <xsl:param name="pPath" select="."/>  -->
	<!-- <xsl:param name="groupValue" />  -->  
	 <xsl:param name="curLocation"/>  
   <xsl:copy>
     <xsl:copy-of select="text()|@*"/>
		 <!-- <xsl:value-of select="$groupVal"/>  -->
	   <xsl:apply-templates select="child::*" mode="gen">
		   <!-- <xsl:with-param name="groupValue" select="$groupValue"/>  -->
		   <xsl:with-param name="curLocation" select="$curLocation"/>  
     </xsl:apply-templates>
   </xsl:copy>
 </xsl:template>
 
 
 <!-- <xsl:template match="void[@property='groupNum']">  -->
 <xsl:template match="int" mode="gen"> 
   <!-- <xsl:param name ="groupValue" />  -->
	  <xsl:param name="curLocation"/>  
	   <xsl:copy>
		   <!-- <xsl:copy-of select="$groupValue"/> -->  
			 <xsl:call-template name="calculateGroup">
         <!-- <xsl:with-param name="groupValue" select="1"/>  -->
				 <xsl:with-param name="curLocation" select="$curLocation" />			
				 <xsl:with-param name="curPath" select="./ancestor::object" />
				 <!-- <xsl:with-param name="prevPath" select="./ancestor::object/preceding-sibling::object[1]" />  --> 
			  <!-- <xsl:value-of select="$groupValue1"/>  -->
			 </xsl:call-template>

		      
		 </xsl:copy>
 </xsl:template>
	
	 
 <xsl:template match="*" mode="gen"> 
  <!-- <xsl:param name="pPath" select="."/>  -->  
	<!-- <xsl:param name="groupValue"/>  -->  
	 <xsl:param name="curLocation"/> 
   <xsl:copy>
     <xsl:copy-of select="text()|@*"/>
		  
		   <!-- don't need to use descendant here because the * match recursively 
			      applies the processing on all its descendant nodes -->
		 				
		   <xsl:apply-templates select="child::*" mode="gen">
			   <!-- <xsl:with-param name="groupValue" select="$groupValue" />  -->  
				 <xsl:with-param name="curLocation" select="$curLocation"/>  
	     </xsl:apply-templates>			
   </xsl:copy>
 </xsl:template>

  <xsl:template name="calculateGroup">
  <xsl:param name="curLocation"/>
	<xsl:param name="curPath"/>
	
 	<xsl:choose>
	  <xsl:when test="number($curLocation) &gt; number('1')" > 
    	<xsl:variable name="prevPath" select="$curPath/preceding-sibling::object[1]" />  
	    <!-- <xsl:variable name="curTimeStamp" select="$curPath/descendant::long" />  
	    <xsl:variable name="prevTimeStamp" select="$prevPath/descendant::long"/> -->
	    <xsl:variable name="prevLocation" select="number($curLocation) - number('1')"/>  
			<xsl:variable name="curView" select="$curPath/void[@property='view']/string"/>

	  <xsl:choose>
	   
		  <!-- <xsl:when test="number($curTimeStamp) - number($prevTimeStamp) &gt; number('1000')" >  -->
			<xsl:when test="contains($curView, 'archipelago')"> 
	    <xsl:variable name="recursive_resultOne">
	  	  <xsl:call-template name="calculateGroup">
	  	      <xsl:with-param name="curLocation" select="$prevLocation"/>
						<xsl:with-param name="curPath" select="$prevPath"/>
		    </xsl:call-template>
		  </xsl:variable>
		  <xsl:value-of select="number('1') + $recursive_resultOne"/> 
	   </xsl:when>
		 
	   <xsl:otherwise>
		 	 <xsl:variable name="recursive_resultTwo">
	  	  <xsl:call-template name="calculateGroup">
	  	      <xsl:with-param name="curLocation" select="$prevLocation"/>
						<xsl:with-param name="curPath" select="$prevPath"/> 
		    </xsl:call-template>
		  </xsl:variable>
		  <xsl:value-of select="$recursive_resultTwo"/> 
			
	   </xsl:otherwise>
		 
	  </xsl:choose>
	 </xsl:when>
	 <xsl:otherwise>
	    <!-- position() = 1 -->
	 		<xsl:value-of select="number('1')"/>  
	 </xsl:otherwise> 
	</xsl:choose>
	<!-- <xsl:value-of select="concat(' cur:', $curTimeStamp, ' prev:', $prevTimeStamp)"/>  --> 
 </xsl:template>

  
 <!-- Stop processing -->
 <xsl:template name="done">
 
 </xsl:template>

</xsl:stylesheet>