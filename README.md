

#Candidate Reduction by Type Inference on AGDISTIS
As a part of an <a href="http://3.okbqa.org">OKBQA</a>(Open Knowledge Base and Question-Answering) Hackathon  disambiguation task, <a href="https://github.com/Seondong/AGDISTIS">Sundong Kim</a> modified the part of AGDISTIS in order to reduce the running time of the AGDISTIS software. Modification is done on CandidateUtil.class.

Basically, the method is follows:
From the candidate sets generated from each entity, candidates which only have few type information are discarded.
As a consequence, smaller subgraph is generated so that the following HITS algorithm performs better. Sophiscated clstering function might boost the performance further(Please contribute to CandidateUtil.findLargestComponent() method <a href="https://github.com/Seondong/AGDISTIS/blob/master/src/main/java/org/aksw/agdistis/algorithm/CandidateUtil.java">here</a>).

Relevent documentation can be found on the following <a href="https://docs.google.com/presentation/d/1ZsZDb8f8hAlHHlOLyQVee2e5w_wTt10iSD2kAH0Vc3E/edit?usp=sharing">link</a>.







#AGDISTIS
AGDISTIS - Agnostic Named Entity Disambiguation. This projects aimes at delivering a framework for disambiguating a priori annotated named entities.

More information about the project can be found <a href="http://aksw.org/projects/AGDISTIS">here</a> and in our <a href="https://github.com/AKSW/AGDISTIS/wiki">Wiki</a>.

Supplementary material can be found in the documents folder.

We hope you will enjoy using AGDISTIS!

### Support and Feedback
If you need help or you have questions do not hesitate to write an email to  <a href="mailto:usbeck@informatik.uni-leipzig.de">Ricardo Usbeck</a>. Or use the issue tracker in the right sidebar.

### How to cite
```Tex
@incollection{AGDISTIS_ISWC,
  author = {Usbeck, Ricardo and {Ngonga Ngomo}, Axel-Cyrille and Auer, S{\"o}ren and Gerber, Daniel and Both, Andreas},
  booktitle = {13th International Semantic Web Conference},
  title = {AGDISTIS - Graph-Based Disambiguation of Named Entities using Linked Data},
  url = {http://svn.aksw.org/papers/2014/ISWC_AGDISTIS/public.pdf},
  year = 2014
}


```

### Acknowlegements
This work has been supported by the ESF and the Free State of Saxony.


## Annotation Tool

The used annotation tool can be downloaded from <a href="https://github.com/RicardoUsbeck/QRTool">here</a>.

## Disclaimer

The deployed webservice does not reflect the optimal parametrization of AGDISTIS as published.

## Bindings
* Python bindings: https://pypi.python.org/pypi/agdistispy/
