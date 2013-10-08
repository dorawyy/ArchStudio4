<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" indent="yes"/>

<xsl:strip-space elements="*" />

<!-- This template goes through each top level object element and determines the files
     that are within a given group 
		 customized for Lunar Lander
		 Use the following classification
		 'Chapter' - domain knowledge links
		 htm, pdf, ppt, txt - rationale links -->
		 
	
 <xsl:template match="/">
  <java version="1.6.0_05" class="java.beans.XMLDecoder">
      <xsl:for-each select="//void[@property='relationship']">
			  <xsl:variable name="curGroupNum" select="./parent::object/descendant::int" />
				
			  <xsl:if test="position() &gt; last()">
			      <xsl:call-template name = "done">
						</xsl:call-template>
			  </xsl:if>
				
				<xsl:choose>
				  <xsl:when test="position() &gt; number('1')">
					    <xsl:variable name="prevGroupNum" select="./parent::object/preceding-sibling::object[1]/descendant::int" />
 			        <xsl:call-template name="copyNodes">
				        <xsl:with-param name="curPosition" select="position()"/>
					      <xsl:with-param name="lastPosition" select="last()"/>
					      <xsl:with-param name="curPath" select="./parent::object"/>
					      <xsl:with-param name="groupNum" select="$curGroupNum"/>
								<!-- <xsl:with-param name="prevGroupNum" select="$prevGroupNum"/> -->
				      </xsl:call-template>
						
					</xsl:when>
					<xsl:otherwise> 
					  <!-- position() = 1 -->
					    <xsl:variable name="prevGroupNum" select="$curGroupNum" />
 			        <xsl:call-template name="copyNodes">
				        <xsl:with-param name="curPosition" select="position()"/>
					      <xsl:with-param name="lastPosition" select="last()"/>
					      <xsl:with-param name="curPath" select="./parent::object"/>
					      <xsl:with-param name="groupNum" select="$curGroupNum"/>
								<!-- <xsl:with-param name="prevGroupNum" select="$prevGroupNum"/> -->
				      </xsl:call-template>
					
					</xsl:otherwise>
				</xsl:choose>
				
				
			
      </xsl:for-each>
  </java>
 </xsl:template>
 
 <xsl:template name="copyNodes">
  <xsl:param name="curPosition"/>
	<xsl:param name="lastPosition"/>
	<xsl:param name="curPath"/>
	<xsl:param name="groupNum"/>
	<!-- <xsl:param name="prevGroupNum"/> -->	
  <xsl:apply-templates select="ancestor::object" mode="gen">
	  <!-- <xsl:with-param name="pPath" select="ancestor::void/parent::object/descendant::*" />  -->
		<xsl:with-param name="curPosition" select="$curPosition"/>
		<xsl:with-param name="lastPosition" select="$lastPosition"/>
		<xsl:with-param name="curPath" select="$curPath"/>
		<xsl:with-param name="groupNum" select="$groupNum"/>
		<!-- <xsl:with-param name="prevGroupNum" select="$prevGroupNum"/> -->
  </xsl:apply-templates>
 </xsl:template>

  
 <xsl:template match="object" mode="gen">  
  <!-- <xsl:param name="pPath" select="."/>  -->
  <xsl:param name="curPosition"/>  
	<xsl:param name="lastPosition"/>
	<xsl:param name="curPath"/>
	<xsl:param name="groupNum"/>
	<!-- <xsl:param name="prevGroupNum"/> --> 
   <xsl:copy>
     <xsl:copy-of select="text()|@*"/>
	   <xsl:apply-templates select="child::*" mode="gen">
		   <xsl:with-param name="curPosition" select="$curPosition"/>
    	 <xsl:with-param name="lastPosition" select="$lastPosition"/>
		   <xsl:with-param name="curPath" select="$curPath"/>
		   <xsl:with-param name="groupNum" select="$groupNum"/>
			 <!-- <xsl:with-param name="prevGroupNum" select="$prevGroupNum"/> -->
     </xsl:apply-templates>
   </xsl:copy>
 </xsl:template>
 
 
 
 <xsl:template match="void[@property='relationship']//string" mode="gen"> 
	<xsl:param name="curPosition"/>  
	<xsl:param name="lastPosition"/>
	<xsl:param name="curPath"/>
	<xsl:param name="groupNum"/>
 	<!-- <xsl:param name="prevGroupNum"/> -->
		
      <xsl:copy>
			  <xsl:call-template name="getRelationship">
				 <xsl:with-param name="curPosition" select="$curPosition" />
				 <xsl:with-param name="lastPosition" select="$lastPosition" />
				 <xsl:with-param name="curPath" select="$curPath" />
				 <xsl:with-param name="groupNum" select="$groupNum" />
				 
  			</xsl:call-template>
		  </xsl:copy>
 </xsl:template>
	
	 
 <xsl:template match="*" mode="gen"> 
   <!-- <xsl:param name="pPath" select="."/>  -->  
	 <xsl:param name="curPosition"/> 
   <xsl:param name="lastPosition"/>
	 <xsl:param name="curPath"/>
	 <xsl:param name="groupNum"/>
   <!-- <xsl:param name="prevGroupNum"/> -->
   <xsl:copy>
     <xsl:copy-of select="text()|@*"/>
		  
		   <!-- don't need to use descendant here because the * match recursively 
			      applies the processing on all its descendant nodes -->
		 				
		   <xsl:apply-templates select="child::*" mode="gen">
			   <!-- <xsl:with-param name="groupValue" select="$groupValue" />  -->  
				 <xsl:with-param name="curPosition" select="$curPosition"/>  
				 <xsl:with-param name="lastPosition" select="$lastPosition" />
				 <xsl:with-param name="curPath" select="$curPath" />
				 <xsl:with-param name="groupNum" select="$groupNum" />
 				 <!-- <xsl:with-param name="prevGroupNum" select="$prevGroupNum"/> -->				 
	     </xsl:apply-templates>			
   </xsl:copy>
 </xsl:template>
 
 
 <xsl:template name="getRelationship">
  <xsl:param name="curPosition"/>
	<xsl:param name="lastPosition"/>
	<xsl:param name="curPath"/>
	<xsl:param name="groupNum"/>
	<xsl:variable name="curFileType" select="$curPath/void[@property='relationship']/string" />
	
 	<xsl:choose>
	  
	  <xsl:when test="number($curPosition) &gt; number('1')" >
		  <xsl:variable name="prevPosition" select="number($curPosition) - number('1')"/> 
    	<xsl:variable name="prevPath" select="$curPath/preceding-sibling::object[1]" />
	    <xsl:variable name="prevGroupNum" select="$prevPath/descendant::int" />
			
			
	  <xsl:choose>
	   
		  <xsl:when test="number($groupNum) = number($prevGroupNum)" > 
	    <xsl:variable name="recursiveResultOne">
	  	  <xsl:call-template name="getRelationship">
	  	      <xsl:with-param name="curPosition" select="$prevPosition"/>
						<xsl:with-param name="lastPosition" select="$lastPosition"/>
						<xsl:with-param name="curPath" select="$prevPath"/>
						<xsl:with-param name="groupNum" select="$groupNum"/>
		    </xsl:call-template>
		  </xsl:variable>
			<xsl:value-of select="$recursiveResultOne"/>

	   </xsl:when>
		 
	   <xsl:otherwise>
			<!-- reached the top of the group -->
			<xsl:choose>
			
			  <!-- 7/29/09 added to distinguish between online resources -->
			  <xsl:when test="contains($curFileType, 'http')">
				  <xsl:choose>
					  <xsl:when test="contains($curFileType, 'wiki')">
			        <xsl:value-of select="string('implementation')"/>
						</xsl:when>
						<xsl:otherwise>
			        <xsl:value-of select="string('rationalization')"/>						
						</xsl:otherwise>
					</xsl:choose>
			  </xsl:when>

				<!-- 7/29/09 -->
			  <!-- <xsl:when test="contains($curFileType, 'htm') or contains($curFileType, 'pdf') or contains($curFileType, 'txt') or contains($curFileType, 'doc')">  -->
				<xsl:when test="contains($curFileType, 'pdf') or contains($curFileType, 'txt') or contains($curFileType, 'doc')">  
	        <xsl:value-of select="string('rationalization')"/>
			  </xsl:when>

			  <xsl:when test="contains($curFileType, 'ppt')">
			    <xsl:value-of select="string('generalization')"/>
			  </xsl:when>
				
			  <xsl:when test="contains($curFileType, 'Chapter')">
			    <xsl:value-of select="string('domain')"/>
			  </xsl:when>

			  <xsl:when test="contains($curFileType, 'xls')">
			    <xsl:value-of select="string('verification')"/>
			  </xsl:when>

				<!-- 7/29/09
			  <xsl:when test="contains($curFileType, 'wiki')">
			    <xsl:value-of select="string('implementation')"/>
			  </xsl:when>
				 -->
				 
				<xsl:otherwise>
				  <xsl:value-of select="string('')"/>
				</xsl:otherwise>
			
			</xsl:choose>

	   </xsl:otherwise>
		 
	  </xsl:choose>
	 </xsl:when>

	 <xsl:otherwise>
	    <!-- We are at the first position.  Do a file type check
			     TODO: this check is exactly the same as at the top.
					       See if this can be refactored into a template -->
			<xsl:choose>
			
				<!-- 7/29/09 added to distinguish between online resources -->
			  <xsl:when test="contains($curFileType, 'http')">
				  <xsl:choose>
					  <xsl:when test="contains($curFileType, 'wiki')">
			        <xsl:value-of select="string('implementation')"/>
						</xsl:when>
						<xsl:otherwise>
			        <xsl:value-of select="string('rationalization')"/>						
						</xsl:otherwise>
					</xsl:choose>
			  </xsl:when>
				
				<!-- 7/29/09 -->
			  <!-- <xsl:when test="contains($curFileType, 'htm') or contains($curFileType, 'pdf') or contains($curFileType, 'txt') or contains($curFileType, 'doc')">  -->
				<xsl:when test="contains($curFileType, 'pdf') or contains($curFileType, 'txt') or contains($curFileType, 'doc')">  
	        <xsl:value-of select="string('rationalization')"/>
			  </xsl:when>

			  <xsl:when test="contains($curFileType, 'ppt')">
			    <xsl:value-of select="string('generalization')"/>
			  </xsl:when>
				
			  <xsl:when test="contains($curFileType, 'Chapter')">
			    <xsl:value-of select="string('domain')"/>
			  </xsl:when>
				
			  <xsl:when test="contains($curFileType, 'xls')">
			    <xsl:value-of select="string('verification')"/>
			  </xsl:when>

			<!-- 7/29/09
			  <xsl:when test="contains($curFileType, 'wiki')">
			    <xsl:value-of select="string('implementation')"/>
			  </xsl:when>
				 -->

				<xsl:otherwise>
				  <xsl:value-of select="string('')"/>
				</xsl:otherwise>
			</xsl:choose>

	 </xsl:otherwise> 
	</xsl:choose>
  <!-- <xsl:value-of select="concat(' curP:', $curPosition, ' lastP', $lastPosition)"/>  -->   
 </xsl:template>
 
  
 <!-- Stop processing -->
 <xsl:template name="done">
 
 </xsl:template>

</xsl:stylesheet>