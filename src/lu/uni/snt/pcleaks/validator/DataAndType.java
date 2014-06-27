package lu.uni.snt.pcleaks.validator;

public class DataAndType 
{
	//: <scheme>://<host>:<port>/[<path>|<pathPrefix>|<pathPattern>]
	private String scheme;
	private String path;
	
	//IntentFilter
	private String host;
	private String port;
	
	//Intent
	private String ssp;
	private String uri;
	private String query;
	private String authority;
	
	//: MimeType
	private String type;
	private String subtype;

	private String indentStr = "    ";
	
	public DataAndType setIndent(int indent)
	{
		indentStr = "";
		for (int i = 0; i < indent; i++)
		{
			indentStr += " ";
		}
		
		return this;
	}
	
	public boolean containingMimeType()
	{
		if (null != type || null != subtype)
		{
			return true;
		}
		
		return false;
	}
	
	public String getMimeType()
	{
		String mimeType = "";
		
		if (type == null)
		{
			mimeType = "*";
		}
		else
		{
			mimeType = type;
		}
		
		mimeType = mimeType + "/";
		
		if (subtype == null)
		{
			mimeType = mimeType + "*";
		}
		else
		{
			mimeType = mimeType + subtype;
		}
		
		return mimeType;
	}
	
	public boolean containingDataExceptMimeType()
	{
		if (! (null == scheme && 
				null == path && 
				null == host && 
				null == port && 
				null == ssp &&
				null == uri &&
				null == query &&
				null == authority))
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(indentStr + "type: " + type + "\n");
		sb.append(indentStr + "subtype: " + subtype + "\n");
		sb.append(indentStr + "scheme: " + scheme + "\n");
		sb.append(indentStr + "path: " + path + "\n");
		sb.append(indentStr + "host: " + host + "\n");
		sb.append(indentStr + "port: " + port + "\n");
		sb.append(indentStr + "ssp: " + ssp + "\n");
		sb.append(indentStr + "uri: " + uri + "\n");
		sb.append(indentStr + "query: " + query + "\n");
		sb.append(indentStr + "authority: " + authority + "\n");
		return sb.toString();
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSsp() {
		return ssp;
	}

	public void setSsp(String ssp) {
		this.ssp = ssp;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
}
