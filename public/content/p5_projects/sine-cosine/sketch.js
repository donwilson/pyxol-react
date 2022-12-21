	
	let ms_per_rev = 3000;   // milliseconds per one rotation
	
	let circle_radius = 64;
	let line_width = 2;
	let dot_radius = (line_width * 3);
	let circle_graph_spacing = 26;   // spacing between circle and graphs
	let dotted_line_spacing = 8;
	let graph_size = (circle_radius * 3);
	let graph_arrow_size = 6;   // size of arrow
	let notation_spacing = 10;   // spacing between graph and notation
	let notation_font_size = 9;
	
	let bg_color;
	let color_line;
	let triangle_color;
	
	let angle = 0.0;
	
	let arrow_pos;   // starting position of each graph directional arrow
	let equation_text;   // equation text
	let equation_width;   // equation text width
	
	function setup() {
		createCanvas(600, 600);
		
		bg_color = color(255, 255, 255);
		color_line = color(0, 0, 0);
		triangle_color = color(249, 218, 118);
	}
	
	function draw() {
		// increase angle
		angle = (360 * (millis() / ms_per_rev));
		angle %= 360;   // wrap angle between 0 and <360
		
		// calculate angle point on circle
		let dot_x = (circle_radius * cos(radians(angle)));
		let dot_y = (circle_radius * sin(radians(angle)));
		
		
		// bg
		background(bg_color);
		
		
		// cirlce
		push();
			translate(width/2, height/2);
			stroke(color_line);
			strokeWeight(line_width);
			ellipseMode(CENTER);
			ellipse(0, 0, circle_radius*2, circle_radius*2);
		pop();
		
		
		// triangle
		push();
			translate(width/2, height/2);
			stroke(color_line);
			strokeWeight(line_width);
			fill(triangle_color);
			triangle(0, 0, dot_x, dot_y, dot_x, 0);
		pop();
		
		
		// x,y dot
		push();
			translate(((width / 2) + dot_x), ((height / 2) + dot_y));
			noStroke();
			fill(color_line);
			ellipseMode(CENTER);
			ellipse(0, 0, (dot_radius * 2), (dot_radius * 2));
		pop();
		
		
		// x dotted line
		push();
			translate(((width / 2) - (circle_radius + circle_graph_spacing)), ((height / 2) + dot_y));
			stroke(color_line);
			strokeWeight(2);
			
			for(let i = 0; i <= (circle_graph_spacing + (circle_radius * 2) + circle_graph_spacing); i += dotted_line_spacing) {
				point(i, 0);
			}
		pop();
		
		
		// y dotted line
		push();
			translate(((width / 2) + dot_x), ((height / 2) - (circle_radius + circle_graph_spacing)));
			stroke(color_line);
			strokeWeight(2);
			
			for(let i = 0; i <= (circle_graph_spacing + (circle_radius * 2) + circle_graph_spacing); i += dotted_line_spacing) {
				point(0, i);
			}
		pop();
		
		
		// positive y graph
		push();   // cosine value line; directional arrow
			// cosine value line
			translate(((width / 2) - circle_radius), ((height / 2) - (circle_radius + circle_graph_spacing)));
			stroke(color_line);
			strokeWeight(line_width);
			line(0, 0, (circle_radius * 2), 0);
			
			// directional arrow
			translate((circle_radius * 2), 0);
			noStroke();
			fill(color_line);
			triangle(0, 0, 0, (-graph_arrow_size / 2), graph_arrow_size, 0);   // above half
			triangle(0, 0, 0, (graph_arrow_size / 2), graph_arrow_size, 0);   // bottom half
		pop();
		
		push();   // 1 to -1 line; cosine value graph
			// 1 to -1 line
			translate((width / 2), ((height / 2) - (circle_radius + circle_graph_spacing + graph_size)));
			stroke(color_line);
			strokeWeight(line_width);
			line(0, 0, 0, graph_size);
			
			// cosine value graph
			beginShape();
			noFill();
			
			for(let i = 0; i <= graph_size; i++) {
				let i_angle = (angle + map(i, 0, graph_size, 0, 360));
				i_angle %= 360;
				
				let seg_x = (circle_radius * cos(radians(i_angle)));
				
				vertex(
					seg_x,
					(graph_size - i)
				);
			}
			
			endShape();
		pop();
		
		
		// negative y graph
		push();   // cosine value line; directional arrow
			// cosine value line
			translate(((width / 2) - circle_radius), ((height / 2) + (circle_radius + circle_graph_spacing)));
			stroke(color_line);
			strokeWeight(line_width);
			line(0, 0, (circle_radius * 2), 0);
			
			// directional arrow
			translate((circle_radius * 2), 0);
			noStroke();
			fill(color_line);
			triangle(0, 0, 0, (-graph_arrow_size / 2), graph_arrow_size, 0);   // above half
			triangle(0, 0, 0, (graph_arrow_size / 2), graph_arrow_size, 0);   // bottom half
		pop();
		
		push();   // 1 to -1 line; cosine value graph; angle directional arrow; angle notation
			// 1 to -1 line
			translate((width / 2), ((height / 2) + (circle_radius + circle_graph_spacing + graph_size)));
			stroke(color_line);
			strokeWeight(line_width);
			line(0, 0, 0, -graph_size);
			
			// cosine value graph
			beginShape();
			noFill();
			
			for(let i = 0; i <= graph_size; i++) {
				let i_angle = (angle + map(i, 0, graph_size, 0, 360));
				i_angle %= 360;
				
				let seg_x = (circle_radius * cos(radians(i_angle)));
				
				vertex(seg_x, -i);
			}
			
			endShape();
			
			// angle directional arrow
			noStroke();
			fill(color_line);
			triangle(0, 0, (-graph_arrow_size / 2), 0, 0, graph_arrow_size);   // above half
			triangle(0, 0, (graph_arrow_size / 2), 0, 0, graph_arrow_size);   // bottom half
			
			// angle notation
			translate(notation_spacing, notation_spacing);
			rotate(radians(90));
			fill(color_line);
			textSize(notation_font_size);
			textStyle(ITALIC);
			text("0", 0, 0);
		pop();
		
		push();   // equation notation
			// equation notation
			translate(((width / 2) + (circle_radius + notation_spacing)), ((height / 2) + (circle_radius + circle_graph_spacing + notation_spacing)));
			rotate(radians(90));
			fill(color_line);
			textSize(notation_font_size);
			textStyle(ITALIC);
			text("x", 0, 0);
			
			equation_text = "x = cos(0)";
			equation_width = textWidth(equation_text);
			translate(((graph_size / 2) - (equation_width / 2)), 0);
			text(equation_text, 0, 0);
		pop();
		
		
		// negative x graph
		push();   // sine value line; directional arrow
			// sin() value line
			translate(((width / 2) - (circle_radius + circle_graph_spacing)), ((height / 2) - circle_radius));
			stroke(color_line);
			strokeWeight(line_width);
			line(0, 0, 0, (circle_radius * 2));
			
			// directional arrow
			noStroke();
			fill(color_line);
			triangle(0, 0, (-graph_arrow_size / 2), 0, 0, -graph_arrow_size);   // above half
			triangle(0, 0, (graph_arrow_size / 2), 0, 0, -graph_arrow_size);   // bottom half
		pop();
		
		push();   // 1 to -1 line; sine value graph
			// 1 to -1 line
			translate(((width / 2) - (circle_radius + circle_graph_spacing + graph_size)), (height / 2));
			stroke(color_line);
			strokeWeight(line_width);
			line(0, 0, graph_size, 0);
			
			// sine value graph
			beginShape();
			noFill();
			
			for(let i = 0; i <= graph_size; i++) {
				let i_angle = (angle + map(i, 0, graph_size, 0, 360));
				i_angle %= 360;
				
				let seg_y = (circle_radius * sin(radians(i_angle)));
				
				vertex((graph_size - i), seg_y);
			}
			
			endShape();
		pop();
		
		
		// positive x graph
		push();   // sine value line; directional arrow
			// sin() value line
			translate(((width / 2) + (circle_radius + circle_graph_spacing)), ((height / 2) - circle_radius));
			stroke(color_line);
			strokeWeight(line_width);
			line(0, 0, 0, (circle_radius * 2));
			
			// directional arrow
			noStroke();
			fill(color_line);
			triangle(0, 0, (-graph_arrow_size / 2), 0, 0, -graph_arrow_size);   // above half
			triangle(0, 0, (graph_arrow_size / 2), 0, 0, -graph_arrow_size);   // bottom half
		pop();
		
		push();   // 1 to -1 line; sine value graph; angle directional arrow; angle notation
			// 1 to -1 line
			translate(((width / 2) + (circle_radius + circle_graph_spacing + graph_size)), (height / 2));
			stroke(color_line);
			strokeWeight(line_width);
			line(0, 0, -graph_size, 0);
			
			// sine value graph
			beginShape();
			noFill();
			
			for(let i = 0; i <= graph_size; i++) {
				let i_angle = (angle + map(i, 0, graph_size, 0, 360));
				i_angle %= 360;
				
				let seg_y = (circle_radius * sin(radians(i_angle)));
				
				vertex(-i, seg_y);
			}
			
			endShape();
			
			// angle directional arrow
			noStroke();
			fill(color_line);
			triangle(0, 0, 0, (-graph_arrow_size / 2), graph_arrow_size, 0);   // above half
			triangle(0, 0, 0, (graph_arrow_size / 2), graph_arrow_size, 0);   // bottom half
			
			// angle notation
			translate(notation_spacing, -notation_spacing);
			fill(color_line);
			textSize(notation_font_size);
			textStyle(ITALIC);
			text("0", 0, 0);
		pop();
		
		push();   // equation notation
			// equation notation
			translate(((width / 2) + (circle_radius + circle_graph_spacing + notation_spacing)), ((height / 2) - (circle_radius + notation_spacing)));
			fill(color_line);
			textSize(notation_font_size);
			textStyle(ITALIC);
			text("y", 0, 0);
			
			equation_text = "y = sin(0)";
			equation_width = textWidth(equation_text);
			translate(((graph_size / 2) - (equation_width / 2)), 0);
			text(equation_text, 0, 0);
		pop();
	}
	