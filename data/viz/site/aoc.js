/**
 * Shared Javascript for Novetta AoC Fastest Times page.
 */
$(document).ready(function() {

	// Event handler for showing/hiding times in the Top X Overall section.
	$("span.tieTimeLink").click(
		function() {
			var place = $(this).attr("id").substring("tieTime".length);
			var oldDisplay = document.getElementById('details' + place).style.display;
			if (oldDisplay == 'block') {
				$('#details' + place).hide(150);
			}
			else {
				$('#details' + place).show(300);
			}
			// Add tiebreaker time glow effect for eligible players.
			if (!document.getElementById('tieTime' + place).classList.contains('ineligible')) {
				document.getElementById('tieTime' + place).style.color =
					(oldDisplay == 'block' ? '#ffffff' : '#888800');
			}
		});

	// Event handle for toggling between total/split times in the Top X Daily section.
	$("span.dailyLink").click(
		function() {
			oldDisplay = document.getElementById('dailySplit').style.display;
			if (oldDisplay == 'none') {
				$('span.dS').hide();
				$('span.dT').show();
			}
			else {
				$('span.dT').hide();
				$('span.dS').show();
			}
		});
	});