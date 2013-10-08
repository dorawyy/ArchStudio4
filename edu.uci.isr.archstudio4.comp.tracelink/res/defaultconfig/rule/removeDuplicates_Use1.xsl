<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" indent="yes"/>

<xsl:strip-space elements="*" />

<!-- This template goes through each top level object element and removes 
     the selection objects that are the same element as the previous element 
		 
		 Run this rule after the timestamp check-->
	
 <xsl:template match="/">
  <java version="1.6.0_05" class="java.beans.XMLDecoder">
			
      <!-- <xsl:for-each select="//void[@property='timeStamp']/object">  -->
			<xsl:for-each select="//void[@property='element']/string">  
			 
			  <xsl:if test="position() &gt; last()">
			      <xsl:call-template name = "done">
						</xsl:call-template>
			  </xsl:if>
				
				<!-- <xsl:variable name="curElement" select="." />  -->
				<xsl:variable name="curElement" select="." /> 
				<xsl:variable name="nextElement" select="./ancestor::object/following-sibling::object[1]/void[@property='element']/string"/>
				<!-- The following are debug statements 
				 curTimeStamp <xsl:value-of select="$curTimeStamp"/> and 
				  nextTimeStamp  <xsl:value-of select="$nextTimeStamp"/>
					-->
					
      	<xsl:choose>
					<!-- <xsl:when test="number($nextTimeStamp) - number($curTimeStamp) &lt; number(1000)">  -->
					<xsl:when test="$curElement = $nextElement">  
 			      <xsl:call-template name = "nextObject">
						</xsl:call-template>
          </xsl:when>
          <xsl:otherwise>
					  <xsl:call-template name = "curObject">
						</xsl:call-template>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:for-each>
  </java>
 </xsl:template>


 <!-- Ignore this entire object branch and go to the next object branch -->
 <xsl:template name="nextObject">
  <xsl:apply-templates select="ancestor::void/parent::object[position()+1]" mode="gen">
	 </xsl:apply-templates>
 </xsl:template>

 <!-- This is a valid selection -->
 <xsl:template name="curObject">
  <xsl:apply-templates select="ancestor::object" mode="gen">
	  <xsl:with-param name="pPath" select="ancestor::void/parent::object/descendant::*" />
  </xsl:apply-templates>
 </xsl:template>
 
 <!-- Stop processing -->
 <xsl:template name="done">
 
 </xsl:template>

 <xsl:template match="object" mode="gen">  
  <xsl:param name="pPath" select="."/>  
   <xsl:copy>
     <xsl:copy-of select="text()|@*"/>
	   <xsl:apply-templates select="child::*" mode="gen">
     </xsl:apply-templates>
   </xsl:copy>
 </xsl:template>
	
	 
 <xsl:template match="*" mode="gen">
  <xsl:param name="pPath" select="."/>  
   <xsl:copy>
     <xsl:copy-of select="text()|@*"/>
		   <!-- don't need to use descendant here because the * match recursively 
			      applies the processing on all its descendant nodes -->
		   <xsl:apply-templates select="child::*" mode="gen">
	     </xsl:apply-templates>			
   </xsl:copy>
 </xsl:template>

</xsl:stylesheet>