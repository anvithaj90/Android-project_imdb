<?php



header('Content-Type:text/html;charset=utf-8');





$testchk=$_GET['title'];
$op = preg_replace('!\s+!','', $testchk);

 if (isset($_GET['title']) && ($_GET['title']!="")&& ($op!="") ) 
{ 
	   
	   $textbox1= $_GET['title'];
	   $textbox11=$textbox1;
	   $textbox1 = strtolower($textbox1);			## to lower case...
	
#$textbox1=str_replace("'", "%27", $textbox1);
  $textbox1=str_replace("&", "%26", $textbox1);
	 	$textbox1=preg_replace('!\s+!', '%20', $textbox1);
		$selectbox1=$_GET['title_type'];
		if($selectbox1!='at')
		$url = 'http://www.imdb.com/search/title?title='.$textbox1.'&title_type='.$selectbox1;
        else{
		$selectbox1="All Types";
		$url = 'http://www.imdb.com/search/title?title='.$textbox1.'&title_type=feature,tv_series,game'; 
		}
		
	}
else
{
	$xop= '<script type="text/javascript">alert("Please enter the title!");window.location.href="http://cs-server.usc.edu:20212/hw6.html"</script>"';
}
	
	
$str = file_get_contents($url);
$output = preg_replace('!\s+!', ' ', $str);
$patt0='#No results#U';
$first_check=preg_match($patt0,$output,$matches);
if($first_check==1)
{
	$selectbox1=$_GET['title_type'];
	if($selectbox1=='feature')
	$selectbox11="feature";
	else if($selectbox1=='tv_series')
	$selectbox11="tv_series";
	else if($selectbox1=='game')
	$selectbox11="game";
	else if($selectbox1=='at')
	$selectbox11="All Types";
	#$xop='<br /><br /><br /><br /><br />Search Result<br />"'.$textbox11.'" of type "'.$selectbox11.'":<br />No Movies found!';
	$xop='NMF!!';
}
else
{
	if($selectbox1=='feature')
	$selectbox11="feature";
	else if($selectbox1=='tv_series')
	$selectbox11="tv_series";
	else if($selectbox1=='game')
	$selectbox11="game";
	else if($selectbox1=='at' ||$selectbox1=="All Types")
	$selectbox11="All Types";
	$patt1='#<table class="results">(.*)</table>#U';
	preg_match($patt1,$output,$matches);
	#if($matches==NULL)
	$pieces = explode(" </tr>", @$matches[0]);
	#else
	#	$pieces = explode(" </tr>", $matches[0]);
	if(count($pieces)-1>5)
		$counter_sub=5;
	else
		$counter_sub=count($pieces)-2;
	$xop= '<?xml version="1.0" encoding="UTF-8"?>';
	$xop=$xop.'<rsp stat="ok">';
	$xop=$xop.'<results total="'.$counter_sub.'">';
	
	
	for ($i=1; $i<=$counter_sub; $i++)
	{	
		
		$xop=$xop.'<result ';
		
		$patt_img='#<img src="(.*)"#U';
		preg_match($patt_img,$pieces[$i],$img_value);
		$xop=$xop.'cover="'.$img_value[1].'" ';
		
		$patt_title='#title="(.*)\(.*\)"#U';
		preg_match($patt_title,$pieces[$i],$title_value);
		$xop=$xop.'title="'.$title_value[1].'" ';
		
		$patt_year='#<span class="year_type">\((....).*\)</span>#U';
		preg_match($patt_year,$pieces[$i],$year_value);
		if($year_value==NULL || $year_value[1]=="????")
			$xop=$xop.'year="N.A." ';
		else
			$xop=$xop.'year="'.$year_value[1].'" ';
			
			
		/*$patt_dir='#Dir: <a href=".*">(.*)</a>#Us';
		preg_match($patt_dir,$pieces[$i],$dir_value);
		if($dir_value==NULL)
			$xop=$xop.'director="N.A." ';
		else
			$xop=$xop.'director="'.$dir_value[1].'" ';
		*/
		
		
		$patt_dir='#Dir:(.*)</span>#Us';
		preg_match($patt_dir,$pieces[$i],$dir_value);
		if($dir_value!=NULL)
		{
		$outputd = preg_replace('!W.*!', '', $dir_value[1]);
		$outputd2=strip_tags($outputd,"<a.*>");
		}
		if($dir_value==NULL)
			$xop=$xop.'director="N.A." ';
		else
			$xop=$xop.'director="'.$outputd2.'" ';
		
		/*$patt_dir='#Dir:(.*)</span>#Us';
		preg_match($patt_dir,$pieces[$i],$dir_value);
		if($dir_value!=NULL)
		{
		$outputd = preg_replace('!W.*!', '', $dir_value[1]);
		$outputd2=strip_tags($outputd,"<a.*>");
		}
		if($dir_value==NULL)
			echo "<td align='center'>N.A.</td>";
		else
			echo "<td align='center'>".$outputd2."</td>";
		*/
		
		
		$patt_rating='#<span class="value">(...)</span>#Us';
		$ret_check=preg_match($patt_rating,$pieces[$i],$rating_value);
		if($ret_check==0)
		{
			$xop=$xop.' rating="-" ';
		}
		else
		$xop=$xop.' rating="'.$rating_value[1].'" ';
		
		
		$patt_link='#<a href="/title/(.*)/"#U';
		preg_match($patt_link,$pieces[$i],$link_value);
		$xop=$xop.'details="http://imdb.com/title/'.$link_value[1].'"';
		
		
		$xop=$xop.'/>';
		
	}	
		$xop=$xop.'</results>';
		$xop=$xop.'</rsp>';
		
		
	
}
	echo $xop;
?>
