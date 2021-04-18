<?php

$request = new HttpRequest();
$request->setUrl('https://community-open-weather-map.p.rapidapi.com/weather');
$request->setMethod(HTTP_METH_GET);

$request->setQueryData([
	'q' => 'London,uk',
	'lat' => '0',
	'lon' => '0',
	'callback' => 'test',
	'id' => '2172797',
	'lang' => 'de',
	'units' => 'metric',
	'mode' => 'xml'
]);

$request->setHeaders([
	'x-rapidapi-key' => 'ff49babd40msh68b0db0bc714a9bp1b3109jsnba51ba82b540',
	'x-rapidapi-host' => 'community-open-weather-map.p.rapidapi.com'
]);

try {
	$response = $request->send();

	echo $response->getBody();
} catch (HttpException $ex) {
	echo $ex;
}