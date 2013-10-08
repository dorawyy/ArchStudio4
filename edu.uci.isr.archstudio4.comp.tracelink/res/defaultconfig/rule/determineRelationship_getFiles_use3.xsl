<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" indent="yes"/>

<xsl:strip-space elements="*" />

<!-- This template goes through each top level object element and determines the files
     that are within a given group -->
	
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
								<xsl:with-param name="prevGroupNum" select="$prevGroupNum"/>
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
								<xsl:with-param name="prevGroupNum" select="$prevGroupNum"/>
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
	<xsl:param name="prevGroupNum"/>	
  <xsl:apply-templates select="ancestor::object" mode="gen">
	  <!-- <xsl:with-param name="pPath" select="ancestor::void/parent::object/descendant::*" />  -->
		<xsl:with-param name="curPosition" select="$curPosition"/>
		<xsl:with-param name="lastPosition" select="$lastPosition"/>
		<xsl:with-param name="curPath" select="$curPath"/>
		<xsl:with-param name="groupNum" select="$groupNum"/>
		<xsl:with-param name="prevGroupNum" select="$prevGroupNum"/>
  </xsl:apply-templates>
 </xsl:template>

  
 <xsl:template match="object" mode="gen">  
  <!-- <xsl:param name="pPath" select="."/>  -->
  <xsl:param name="curPosition"/>  
	<xsl:param name="lastPosition"/>
	<xsl:param name="curPath"/>
	<xsl:param name="groupNum"/>
	<xsl:param name="prevGroupNum"/> 
   <xsl:copy>
     <xsl:copy-of select="text()|@*"/>
	   <xsl:apply-templates select="child::*" mode="gen">
		   <xsl:with-param name="curPosition" select="$curPosition"/>
    	 <xsl:with-param name="lastPosition" select="$lastPosition"/>
		   <xsl:with-param name="curPath" select="$curPath"/>
		   <xsl:with-param name="groupNum" select="$groupNum"/>
			 <xsl:with-param name="prevGroupNum" select="$prevGroupNum"/>
     </xsl:apply-templates>
   </xsl:copy>
 </xsl:template>
 
 
 
 <xsl:template match="void[@property='relationship']//string" mode="gen"> 
	<xsl:param name="curPosition"/>  
	<xsl:param name="lastPosition"/>
	<xsl:param name="curPath"/>
	<xsl:param name="groupNum"/>
 	<xsl:param name="prevGroupNum"/>
			
	<!-- <xsl:param name="prevGroupNum"/>  -->
		
	<xsl:choose>
		<!-- check if it is the first position -->
		<xsl:when test="number($curPosition) = 1">
      <xsl:copy>
			  <xsl:call-template name="getFileTypes">
				 <xsl:with-param name="curPosition" select="$curPosition" />
				 <xsl:with-param name="lastPosition" select="$lastPosition" />
				 <xsl:with-param name="curPath" select="$curPath" />
				 <xsl:with-param name="groupNum" select="$groupNum" />
				 
  			</xsl:call-template>
		  </xsl:copy>
		
		</xsl:when>
		
		<!-- check if it is not the first position but the groupNum is different  -->
    <xsl:otherwise>
		 <xsl:choose>
	    <xsl:when test="number($groupNum) != number($prevGroupNum)">
        <xsl:copy>
			    <xsl:call-template name="getFileTypes">
				    <xsl:with-param name="curPosition" select="$curPosition" />
				    <xsl:with-param name="lastPosition" select="$lastPosition" />
				    <xsl:with-param name="curPath" select="$curPath" />
				    <xsl:with-param name="groupNum" select="$groupNum" />
  			  </xsl:call-template>
		     </xsl:copy>
		  </xsl:when>

      <!-- otherwise, just copy the nodes -->
		  <xsl:otherwise>
         <xsl:copy>
           <xsl:copy-of select="text()|@*"/>
					 <!-- 
		       <xsl:apply-templates select="." mode="gen">  
	  	       <xsl:with-param name="curPosition" select="$curPosition"/> 
				     <xsl:with-param name="lastPosition" select="$lastPosition" />
				     <xsl:with-param name="curPath" select="$curPath" />
				     <xsl:with-param name="groupNum" select="$groupNum" />
		 				 <xsl:with-param name="prevGroupNum" select="$prevGroupNum"/>
           </xsl:apply-templates>
					  -->
         </xsl:copy>
		  </xsl:otherwise>

		 </xsl:choose>
		</xsl:otherwise> <!-- end for handling position() > 1 -->



  </xsl:choose>
 </xsl:template>
	
	 
 <xsl:template match="*" mode="gen"> 
   <!-- <xsl:param name="pPath" select="."/>  -->  
	 <xsl:param name="curPosition"/> 
   <xsl:param name="lastPosition"/>
	 <xsl:param name="curPath"/>
	 <xsl:param name="groupNum"/>
   <xsl:param name="prevGroupNum"/>
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
 				 <xsl:with-param name="prevGroupNum" select="$prevGroupNum"/>				 
	     </xsl:apply-templates>			
   </xsl:copy>
 </xsl:template>
 
 
 <xsl:template name="getFileTypes">
  <xsl:param name="curPosition"/>
	<xsl:param name="lastPosition"/>
	<xsl:param name="curPath"/>
	<xsl:param name="groupNum"/>
	<xsl:variable name="curElement" select="$curPath/void[@property='element']/string" />
	
 	<xsl:choose>
	  
	  <xsl:when test="number($curPosition) &lt; number($lastPosition)" >
		  <xsl:variable name="nextPosition" select="number($curPosition) + number('1')"/> 
    	<xsl:variable name="nextPath" select="$curPath/following-sibling::object[1]" />
	    <xsl:variable name="nextGroupNum" select="$nextPath/descendant::int" />
			
			
	  <xsl:choose>
	   
		 <xsl:when test="number($groupNum) = number($nextGroupNum)" > 
	    <xsl:variable name="recursiveResultOne">
	  	  <xsl:call-template name="getFileTypes">
	  	      <xsl:with-param name="curPosition" select="$nextPosition"/>
						<xsl:with-param name="lastPosition" select="$lastPosition"/>
						<xsl:with-param name="curPath" select="$nextPath"/>
						<xsl:with-param name="groupNum" select="$groupNum"/>
		    </xsl:call-template>
		  </xsl:variable>
			
			<!-- Check for the types of files that are present -->
			<xsl:choose>
			  <xsl:when test="contains($curElement, '.ppt')">
			    <xsl:value-of select="concat('ppt ',$recursiveResultOne)"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.pdf')">
			    <xsl:value-of select="concat('pdf ',$recursiveResultOne)"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.jpg')">
			    <xsl:value-of select="concat('jpg ',$recursiveResultOne)"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.xls')">
			    <xsl:value-of select="concat('xls ',$recursiveResultOne)"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.doc')">
			    <xsl:value-of select="concat('doc ',$recursiveResultOne)"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.htm')">
			    <xsl:value-of select="concat('htm ',$recursiveResultOne)"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.txt')">
			    <xsl:value-of select="concat('txt ',$recursiveResultOne)"/>
			  </xsl:when>
				
				
				<xsl:otherwise>
				  <!-- does not have the above file types -->
				  <xsl:value-of select="$recursiveResultOne"/>
				</xsl:otherwise>
			
			</xsl:choose>

	   </xsl:when>
		 
	   <xsl:otherwise>
		   <!-- next group number is different
			      return the current file type -->
			<xsl:choose>
			  <xsl:when test="contains($curElement, '.ppt')">
			    <xsl:value-of select="string('ppt ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.pdf')">
			    <xsl:value-of select="string('pdf ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.jpg')">
			    <xsl:value-of select="string('jpg ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.xls')">
			    <xsl:value-of select="string('xls ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.doc')">
			    <xsl:value-of select="string('doc ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.htm')">
			    <xsl:value-of select="string('htm ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.txt')">
			    <xsl:value-of select="string('txt ')"/>
			  </xsl:when>
				
				
				<xsl:otherwise>
				  <xsl:value-of select="''"/>
				</xsl:otherwise>
			</xsl:choose>	  
	   </xsl:otherwise>
		 
	  </xsl:choose>
	 </xsl:when>

	 <xsl:otherwise>
	    <!-- We are at the last position.  Check the file type and return it 
			     TODO: refactor the check file type above and the one below -->
			<xsl:choose>
			  <xsl:when test="contains($curElement, '.ppt')">
			    <xsl:value-of select="string('ppt ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.pdf')">
			    <xsl:value-of select="string('pdf ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.jpg')">
			    <xsl:value-of select="string('jpg ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.xls')">
			    <xsl:value-of select="string('xls ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.doc')">
			    <xsl:value-of select="string('doc ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.htm')">
			    <xsl:value-of select="string('htm ')"/>
			  </xsl:when>
			  <xsl:when test="contains($curElement, '.txt')">
			    <xsl:value-of select="string('txt ')"/>
			  </xsl:when>
				
				
				<xsl:otherwise>
				  <xsl:value-of select="''"/>
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