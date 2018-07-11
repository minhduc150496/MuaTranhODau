<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="x" page-width="8.5in" 
                                       page-height="11in" 
                                       margin-top="0.5in" margin-bottom="36pt" 
                                       margin-right="1in" margin-left="1in">
                    <fo:region-body margin-top="0.5in" margin-bottom="0.5in"/>
                    <fo:region-before extent="1in"/>
                    <fo:region-after extent=".75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>            
            <fo:page-sequence master-reference="x">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="14pt" font-family="Helvetica, Arial, sans-serif"
                              line-height="24pt" background-color="#0066cc" color="white"
                              space-after.optimum="15pt" text-align="center" font-weight="bold"
                              padding-top="3pt">
                        TranhĐâyRồi.com - Danh mục sản phẩm
                    </fo:block>
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center" padding-before="1.5cm">
                        Trang <fo:page-number/> / 
                        <fo:page-number-citation 
                            ref-id="TheVeryLastPage"/>
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <fo:table border-collapse="separate" table-layout="fixed" 
                                  font-family="Helvetica, Arial, sans-serif" >
                            <fo:table-column column-width="33.33%"/>
                            <fo:table-column column-width="33.33%"/>
                            <fo:table-column column-width="33.33%"/>
                        
                            <fo:table-body>
                                <xsl:for-each select="paintings/painting">
                                    <xsl:if test="position() mod 3 = 1">                                        
                                        <xsl:text disable-output-escaping="yes">&lt;</xsl:text>fo:table-row keep-together.within-page="always"<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
                                    </xsl:if>
                                    <fo:table-cell border-color="gray"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="center" margin-top="5pt">
                                            <fo:external-graphic width="90%"                                                                  
                                                                 content-width="scale-to-fit"
                                                                 content-height="100%"
                                                                 scaling="uniform">
                                                <xsl:attribute name="src">
                                                    <xsl:value-of select="imageURL"/>
                                                </xsl:attribute>
                                            </fo:external-graphic>
                                        </fo:block>
                                        <fo:block text-align="center" margin-top="5pt">
                                            <xsl:value-of select="position()"/>.
                                            <xsl:value-of select="name"/>
                                        </fo:block>                                        
                                        <fo:block text-align="center" font-size="10pt" font-weight="light"
                                            color="gray">
                                            Mã SP: <xsl:value-of select="code"/>
                                        </fo:block>
                                        <fo:block text-align="center" color="red" font-weight="bold"
                                            margin-bottom="15pt">
                                            <xsl:value-of select="format-number(price, '###,###')"/>đ
                                        </fo:block>
                                    </fo:table-cell>   
                                    <xsl:if test="(position() mod 3 = 0) or (position() = last())">                 
                                        <xsl:text disable-output-escaping="yes">&lt;</xsl:text>/fo:table-row<xsl:text disable-output-escaping="yes">&gt;</xsl:text>
                                    </xsl:if>                                 
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                    <fo:block id="TheVeryLastPage"></fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
</xsl:stylesheet>
