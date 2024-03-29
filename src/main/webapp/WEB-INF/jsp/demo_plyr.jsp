<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>demo plyr</title>
    <link rel="stylesheet" href="https://cdn.plyr.io/3.6.12/plyr.css">
    <style>
        /* This is purely for the demo */
        .container {
            max-width: 800px;
            margin: 0 auto;
        }

        .plyr {
            border-radius: 4px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>

<div class="container">
    <video controls crossorigin playsinline
           poster="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-HD.jpg" id="player">
        <!-- Video files -->
        <source src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-576p.mp4" type="video/mp4"
                size="576">
        <source src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-720p.mp4" type="video/mp4"
                size="720">
        <source src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-1080p.mp4" type="video/mp4"
                size="1080">
        <source src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-1440p.mp4" type="video/mp4"
                size="1440">

        <!-- Caption files -->
        <track kind="captions" label="English" srclang="en"
               src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-HD.en.vtt"
               default>
        <track kind="captions" label="Français" srclang="fr"
               src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-HD.fr.vtt">

        <!-- Fallback for browsers that don't support the <video> element -->
        <a href="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-576p.mp4" download>Download</a>
    </video>

    <div class="actions">
        <button type="button" class="btn js-play">Play</button>
        <button type="button" class="btn js-pause">Pause</button>
        <button type="button" class="btn js-stop">Stop</button>
        <button type="button" class="btn js-rewind">Rewind</button>
        <button type="button" class="btn js-forward">Forward</button>
    </div>
</div>

<script src="https://cdn.plyr.io/3.6.12/plyr.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        // This is the bare minimum JavaScript. You can opt to pass no arguments to setup.
        const player = new Plyr('#player');

        // Expose
        window.player = player;

        // Bind event listener
        function on(selector, type, callback) {
            document.querySelector(selector).addEventListener(type, callback, false);
        }

        // Play
        on('.js-play', 'click', () => {
            player.play();
        });

        // Pause
        on('.js-pause', 'click', () => {
            player.pause();
        });

        // Stop
        on('.js-stop', 'click', () => {
            player.stop();
        });

        // Rewind
        on('.js-rewind', 'click', () => {
            player.rewind();
        });

        // Forward
        on('.js-forward', 'click', () => {
            player.forward();
        });
    });
</script>
</body>
</html>