<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <table border="2">
            <thead>
                <tr>
                    <th>code</th>
                    <th>name</th>
                    <th>image</th>
                    <th>price</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="//painting">
                    <xsl:sort select="name"/>
                    <tr>
                        <td>
                            <xsl:value-of select="code"/>
                        </td>
                        <td>
                            <a href="{pageURL}">
                                <xsl:value-of select="name"/>
                            </a>
                        </td>
                        <td>
                            <img width="100" src="{imageURL}"/>
                        </td>
                        <td>
                            <xsl:value-of select="price"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>

</xsl:stylesheet>
