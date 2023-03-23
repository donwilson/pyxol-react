export default function MarkdownViewer({ parsedMarkdownHTML, baseDir }) {
	// update baseDir path
	baseDir = baseDir.replace(/^public\//i, "");
	
	// fix various html elements
	parsedMarkdownHTML = parsedMarkdownHTML.replaceAll(/<h(1|2|3|4|5|6)([^>]*?)>(.*?)<\/h(1|2|3|4|5|6)>/ig, "<div class=\"h$1\"$2>$3</div>");
	parsedMarkdownHTML = parsedMarkdownHTML.replaceAll(/<img([^>]*?)src="([^"]+?)"/ig, "<img$1src=\"/"+ baseDir +"/$2\"");
	parsedMarkdownHTML = parsedMarkdownHTML.replaceAll(/<a\s+([^>]+?)>/ig, (match) => {
		if(!match.match(/\s+target=/i)) {
			match = match.replace(/>/i, " target=\"_blank\">");
		}
		
		return match;
	});
	
	return (<>
		<div className="bg-light border p-2">
			<div dangerouslySetInnerHTML={{ __html: parsedMarkdownHTML }} />
		</div>
	</>);
}