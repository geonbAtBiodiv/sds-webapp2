

<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <meta name="layout" content="${grailsApplication.config.skin.layout}" />
    <title>Sensitive Data Service</title>
</head>

<body>
<h2>Sensitive Data Service</h2>
The Sensitive Data Service (SDS) is the mechanism for providing security over data sensitivities. At the moment it supports:
<ul>
    <li>Conservation Sensitivity, whereby sensitive species are denatured according to state rules. </li>
    <li>Pest Sensitivity. Species are passed through a set of rules based on categories as defined in the <b>Plant Biosecurity Sensitive Data Service</b></li>
</ul>
For more information see <a href="${grailsApplication.config.sdsFAQ.url}">Data Sensitivity</a>.
<h2>Testing</h2>
<p>
The Sensitive Data Service can be tested through the <a href="${grailsApplication.config.sandbox.url}">sandbox</a>. Simply upload your list of species and coordinates and the
individual record pages of the sandbox will indicate whether or not sensitivity was detected.
</p>
<h2>Resources</h2>
<p>The Sensitive Data Service is controlled through a set of XML files.</p>

    <table class="table">
        <thead>
         <tr>
             <td>File</td>
             <td>Purpose</td>
             <td></td>
             <td></td>
         </tr>
        </thead>
        <tbody>
            <tr>
                <td><a href="${request.contextPath}/sensitive-species-data.xml">Sensitive Species Data</a></td>
                <td>The xml file that supplies all the sensitive species and the categories and zones to which they belong.</td>
                <td>This file was last generated on ${updateDate}.</td>
                <td><g:if test="${request.isUserInRole('ROLE_ADMIN')}" >
                    <a  class="btn" href="${request.contextPath}/refresh">Refresh</a>
                    </g:if>
                </td>
            </tr>
            <tr>
                <td><a href="${request.contextPath}/sensitivity-categories.xml">Sensitive Categories</a></td>
                <td>The xml file that supplies the vocabulary for the sensitive categories.</td>
                <td>This is a static file.</td>
                <td></td>
            </tr>
            <tr>
                <td><a href="${request.contextPath}/sensitivity-zones.xml">Sensitive Zones</a></td>
                <td>The xml file that supplies the vocabulary for the sensitive zones.</td>
                <td>This is a static file.</td>
                <td></td>
            </tr>
            <tr>
                <td><a href="${request.contextPath}/ws/layers">List of sensitive layer IDs</a></td>
                <td>JSON list of layers that are required by the SDS</td>
                <td></td>
                <td></td>
            </tr>
        </tbody>
    </table>
%{--<ul>--}%
    %{--<li> <a href="${request.contextPath}/sensitive-species-data.xml">sensitive-species-data.xml</a>--}%
        %{--<p>Last generated from the <a href="http://lists.ala.org.au/public/speciesLists?isSDS=eq:true">SDS lists</a> on ${updateDate}</p>--}%
        %{--<g:if test="${request.isUserInRole('ROLE_ADMIN')}" >--}%
            %{--<p>Force a manual <a href="${request.contextPath}/refresh">refresh</a></p>--}%
        %{--</g:if>--}%
    %{--</li>--}%
    %{--<li> <a href="${request.contextPath}/sensitivity-categories.xml">sensitivity-categories.xml</a></li>--}%
    %{--<li> <a href="${request.contextPath}/sensitivity-zones.xml">sensitivity-zones.xml</a></li>--}%
%{--</ul>--}%

The species that make up the individual components of the SDS can be viewed via the <a href="${grailsApplication.config.list.tool.url}/public/speciesLists?isSDS=eq:true">list tool</a>.
</body>
</html>