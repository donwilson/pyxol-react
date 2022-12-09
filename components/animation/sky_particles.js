"use client";

import { useEffect } from "react";

import styles from "../../styles/animations/sky_particles.module.css";

export default function SkyParticlesAnimation() {
	useEffect(() => {
		const navbar_el = document.getElementsByClassName("navbar")[0];
		const canvas_el = document.getElementById('sky_particles');
		let ctx = false;
		let max_num_dots = 150;
		let dots = [];
		let max_distance = 150;
		let dot_radius = 2;
		let dot_speed = 2;   // px per second
		let last_time_called = 0;
		
		function create_dot() {
			return {
				'posX': (Math.random() * canvas_el.width),
				'posY': (Math.random() * canvas_el.height),
				'movX': ((Math.random() * 2) - 1),
				'movY': ((Math.random() * 2) - 1),
			};
		}
		
		function calculate_dots() {
			let width = canvas_el.width;
			let height = canvas_el.height;
			
			let num_dots = Math.ceil(Math.sqrt(height));
			
			if(width > height) {
				num_dots = Math.ceil(Math.sqrt(width));
				num_dots *= (3 * (height / width));
			} else {
				num_dots *= (3 * (width / height));
			}
			
			if(max_num_dots && (num_dots > max_num_dots)) {
				num_dots = max_num_dots;
			}
			
			if(dots.length == num_dots) {
				return;
			}
			
			if(dots.length < num_dots) {
				// too few dots
				let num_add = (num_dots - dots.length);
				
				for(let i = 0; i < num_add; i++) {
					dots.push(create_dot());
				}
			} else {
				// too many dots
				dots = dots.slice(0, num_dots);
			}
		}
		
		function moveDot(i, speed) {
			let width = canvas_el.width;
			let height = canvas_el.height;
			
			// wrap around:
			let newX = (dots[ i ].posX + (dots[ i ].movX * speed));
			let newY = (dots[ i ].posY + (dots[ i ].movY * speed));
			
			// check bounds, recreate dot if needed
			if(newX < 0) {
				newX = ((width - Math.abs(newX)) % width);
			} else if(newX > width) {
				newX = (newX % width);
			}
			
			if(newY < 0) {
				newY = ((height - Math.abs(newY)) % height);
			} else if(newY > height) {
				newY = (newY % height);
			}
			
			dots[ i ].posX = newX;
			dots[ i ].posY = newY;
			
			/*// recreate dot if out of display
			dots[ i ].posX += (dots[ i ].movX * speed);
			dots[ i ].posY += (dots[ i ].movY * speed);
			
			if((dots[ i ].posX < 0) || (dots[ i ].posY > width) || (dots[ i ].posY < 0) || (dots[ i ].posY > height)) {
				dots[ i ] = create_dot();
			}*/
		}
		
		function draw() {
			let width = canvas_el.width;
			let height = canvas_el.height;
			
			// clear
			ctx.clearRect(0, 0, width, height);
			
			let now = performance.now();
			let tick = ((now - last_time_called) * (1 / 60));
			last_time_called = now;
			
			let dampened_speed = (dot_speed * tick);
			
			// update movements
			for(let i = 0; i < dots.length; i++) {
				moveDot(i, dampened_speed);
			}
			
			// draw
			for(let i = 0; i < dots.length; i++) {
				let dot = dots[ i ];
				
				// connection lines
				if(i < (dots.length - 1)) {
					for(let j = (i + 1); j < dots.length; j++) {
						let other_dot = dots[ j ];
						
						let distance = Math.sqrt(Math.pow((other_dot.posX - dot.posX), 2) + Math.pow((other_dot.posY - dot.posY), 2));
						
						if(distance <= max_distance) {
							let distance_ratio = (1 - (distance / max_distance));
							distance_ratio *= 0.5;
							
							ctx.save();
							
							ctx.strokeStyle = "rgba(125, 125, 125, "+ distance_ratio +")";
							//ctx.strokeStyle = "rgb(255, 255, 255)";
							
							ctx.beginPath();
							ctx.moveTo(dot.posX, dot.posY);
							ctx.lineTo(other_dot.posX, other_dot.posY);
							ctx.stroke();
							ctx.closePath();
							
							ctx.restore();
						}
					}
				}
				
				// dot ellipse
				ctx.beginPath();
				ctx.fillStyle = "rgba(255, 255, 255, 0.2)";
				ctx.arc(dot.posX, dot.posY, dot_radius, 0, 2 * Math.PI);
				ctx.fill();
				ctx.closePath();
			}
			
			// request new frame update
			window.requestAnimationFrame(draw);
		}
		
		function SP_resizeCanvas() {
			let desired_width = window.innerWidth;
			let desired_height = window.innerHeight;
			
			//console.log("desired_height(before)=", desired_height);
			
			desired_height -= navbar_el.offsetHeight;
			
			//console.log("desired_height(after)=", desired_height);
			
			canvas_el.width = desired_width;
			canvas_el.height = desired_height;
			
			calculate_dots();
		}
		
		if(canvas_el.getContext) {
			ctx = canvas_el.getContext('2d');
			last_time_called = performance.now();
			
			// start animation
			SP_resizeCanvas();
			
			// event: resize canvas on window resize
			window.addEventListener("resize", SP_resizeCanvas, false);
			
			// event: animation drawing
			window.requestAnimationFrame(draw);
		}
	}, []);
	
	return (
		<>
			<canvas
				id="sky_particles"
				width="100%"
				height="60%"
				className={styles.sky_particles_canvas}
			/>
		</>
	);
}